package com.muabanbds.common_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //Lỗi hệ thống
    MISSING_INFORMATION(1000, "Thông tin bị thiếu!", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(9999, "Tài khoản chưa được xác thức!", HttpStatus.UNAUTHORIZED),
    NOT_EXISTED_USERNAME(1001, "Tên đăng nhập hoặc mật khẩu không chính xác", HttpStatus.BAD_REQUEST),
    PERMISSION_INVALID(2001, "Một hoặc nhiều quyền không hợp lệ", HttpStatus.BAD_REQUEST),
    ROLE_INVALID(2002, "Một hoặc nhiều vai trò không hợp lệ", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXIST(4001, "Vai trò không tồn tại!", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_EXIST(4002, "Tài khoản không tồn tại!", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(4002, "Người dùng không tồn tại!", HttpStatus.BAD_REQUEST)
    ;

    int code;
    String message;
    HttpStatus status;
}

