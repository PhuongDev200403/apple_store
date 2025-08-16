package com.nguyenvanphuong.apple_devices.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.service.annotation.GetExchange;

@Getter
public enum ErrorCode {
    UNCATEGORIZED(999, "Uncategory error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED(9999, "Không có quyền truy cập", HttpStatus.FORBIDDEN),
    USER_EXISTED(1000, "Tên người dùng đã tồn tại", HttpStatus.BAD_REQUEST),
    LOGIN_FAILED(1001, "Thông tin tài khoản không chính xác", HttpStatus.BAD_REQUEST),
    ;
    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
