package com.muabanbds.identity_service.service.impl;

import com.muabanbds.common_service.dto.identityDto.request.AccountRequest;
import com.muabanbds.common_service.dto.identityDto.response.AccountResponse;
import com.muabanbds.common_service.dto.identityDto.response.PermissionResponse;
import com.muabanbds.common_service.exception.AppException;
import com.muabanbds.common_service.exception.ErrorCode;
import com.muabanbds.common_service.payload.ApiResponse;
import com.muabanbds.common_service.payload.ApiResponsePagination;
import com.muabanbds.identity_service.entity.*;
import com.muabanbds.identity_service.repository.AccountRepository;
import com.muabanbds.identity_service.repository.AccountRoleRepository;
import com.muabanbds.identity_service.repository.RoleRepository;
import com.muabanbds.identity_service.repository.UserRepository;
import com.muabanbds.identity_service.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;
    UserRepository userRepository;
    RoleRepository roleRepository;
    AccountRoleRepository accountRoleRepository;

    ModelMapper modelMapper;

    @Override
    public ApiResponsePagination<List<AccountResponse>> findAll(AccountRequest request) {
        log.info("***Log account service - get account by username***");
        log.info("{req} :" + request);



        return null;
    }

    @Override
    public ApiResponse<AccountResponse> findById(Integer integer) {
        return null;
    }

    @Override
    public ApiResponse<AccountResponse> save(AccountRequest req) {
        log.info("***Log account service - save account ***");
        log.info("{req} :" + req);
        Account account = null;

        if(req.getUser() != null){
            User user = modelMapper.map(req.getUser(), User.class);
            user = userRepository.save(user);
            log.info("{user} :" + user);

            account = modelMapper.map(req, Account.class);
            account.setUserId(user.getId());
            account = accountRepository.save(account);
            log.info("{account} :" + account);

            if(req.getRoles() != null && !req.getRoles().isEmpty()){
                List<Role> roles = roleRepository.findRolesByNames(req.getRoles());

                if(roles.size() != req.getRoles().size()){
                    throw new AppException(ErrorCode.ROLE_INVALID);
                }

                List<AccountRole> accountRoles = new ArrayList<>();
                for(Role role : roles){
                    AccountRole accountRole = AccountRole.builder()
                            .accountId(account.getId())
                            .roleId(role.getId())
                            .build();
                    accountRoles.add(accountRole);
                }

                accountRoleRepository.saveAll(accountRoles);
            }
        }else{
            throw new AppException(ErrorCode.MISSING_INFORMATION);
        }

       return ApiResponse.<AccountResponse>builder()
               .result(modelMapper.map(account, AccountResponse.class))
               .build();
    }

    @Override
    public ApiResponse<AccountResponse> update(Integer id, AccountRequest req) {
        log.info("***Log account service - update account ***");
        log.info("{req} :" + req);
        Account account = accountRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST));

        User user = userRepository.findById(account.getId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        if(req.getUser() != null){
            modelMapper.map(user, req.getUser());
            userRepository.save(user);
        }

        if(req.getRoles() != null && !req.getRoles().isEmpty()){
            List<Role> roles = roleRepository.findRolesByNames(req.getRoles());

            if(roles.size() != req.getRoles().size()){
                throw new AppException(ErrorCode.ROLE_INVALID);
            }

            accountRoleRepository.cancelAllRoleByAccountId(account.getId());
            List<AccountRole> accountRoles = new ArrayList<>();
            for(Role role : roles){
                AccountRole accountRole = accountRoleRepository.findByAccountIdAndRoleId(account.getId(), role.getId());

                if(accountRole != null) {
                    accountRole.setIsActive("Y");
                }else{
                    accountRole = AccountRole.builder()
                            .accountId(account.getId())
                            .roleId(role.getId())
                            .build();
                }

                accountRoles.add(accountRole);
            }

            accountRoleRepository.saveAll(accountRoles);
        }


        return ApiResponse.<AccountResponse>builder()
                .result(modelMapper.map(account, AccountResponse.class))
                .build();
    }

    @Override
    public ApiResponse<String> deleteById(Integer integer) {
        return null;
    }

    @Override
    public ApiResponse<AccountResponse> findByUsername(String username) {
        log.info("***Log account service - get account by username***");
        log.info("{dto} :" + username);
        Account account = accountRepository.findByUsername(username);
        if(account == null){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return ApiResponse.<AccountResponse>builder()
                .result(modelMapper.map(account, AccountResponse.class))
                .build();
    }
}
