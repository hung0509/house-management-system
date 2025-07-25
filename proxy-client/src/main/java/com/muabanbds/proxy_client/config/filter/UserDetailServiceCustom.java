package com.muabanbds.proxy_client.config.filter;

import com.muabanbds.common_service.dto.identityDto.response.AccountResponse;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.proxy_client.business.identity.service.AccountClientService;
import org.apache.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailServiceCustom implements UserDetailsService {
    AccountClientService accountClientService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApiResponse<AccountResponse> response = accountClientService.findByUsername(username).getBody();

        if(response != null && response.getCode() != HttpStatus.SC_OK){

            AccountResponse accountResponse = response.getResult();
            return UserDetailCustom.builder()
                    .userId(accountResponse.getUserId())
                    .username(accountResponse.getUsername())
                    .password(accountResponse.getPassword())
                    .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                    .build();
        }else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
