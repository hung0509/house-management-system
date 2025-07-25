package com.muabanbds.proxy_client.jwt;

import com.muabanbds.common_service.dto.identityDto.response.AccountResponse;
import com.muabanbds.common_service.dto.identityDto.response.RoleResponse;
import com.muabanbds.common_service.exception.AppException;
import com.muabanbds.common_service.exception.ErrorCode;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtil {
    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    @Value("${jwt.valid-duration}")
    private Long VALID_DURATION;

    @Value("${jwt.refreshable-duration}")
    private long REFRESHABLE_DURATION;

    //Use extract claim
    public <T> T extractClaims(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .setSigningKey(SIGNER_KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    public String extractUsername(final String token) {
        return this.extractClaims(token, Claims::getSubject);
    }

    public String extractUuid(final String token) {
        return this.extractClaims(token, Claims::getId);
    }

    public Integer extractUserID(String token) {
        return Optional.ofNullable(this.extractClaims(token, claims -> claims.get("userId", Integer.class)))
                .orElse(0);
    }

    public Date extractExpiration(final String token) {
        return this.extractClaims(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(final String token) {
        return this.extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(final String token, final UserDetails userDetails) {
        final String username = this.extractUsername(token);
        boolean isCheck = true;

        try {
            verify(token, false);// xác thực không phải là refresh
        }catch(AppException | JOSEException | ParseException appException){
            isCheck = false;
        }
        return (isCheck && username.equals(userDetails.getUsername()));
    }

    public String generateToken(AccountResponse account){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(account.getUsername())
                .issuer("xuan-hung")
                .jwtID(UUID.randomUUID().toString())
                .claim("userId", account.getUserId())
                .claim("scope", buildScope(account))
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    public SignedJWT verify(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        boolean isValid = signedJWT.verify(verifier);

        Date expireDate = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet()
                .getExpirationTime();


        String id_token = signedJWT.getJWTClaimsSet().getJWTID();

        if(!isValid || (expireDate.before(new Date())) )
            throw new AppException(ErrorCode.UNAUTHENTICATED);


        //Kiểm tra xem có trong black list không
//        if(invalidatedTokenRepository.existsByTokenId(id_token)) {
//            log.info("invalidated token");
//            throw new AppException(ErrorCode.UNAUTHENTICATED);
//        }
        return signedJWT;
    }

    //Add role and permission
    private String buildScope(AccountResponse account){
        log.info(account.getRoles().toString());
        StringJoiner joiner = new StringJoiner(" ");
        if(account.getRoles() != null){
            for(RoleResponse role : account.getRoles()){
                joiner.add("ROLE_" + role.getName());

                if(!CollectionUtils.isEmpty(role.getPermissions())){
                    role.getPermissions().forEach(permission -> joiner.add(permission.getName()));
                }
            }
        }
        return joiner.toString();
    }
}
