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
    CATEGORY_CHILD_EXISTED(1002, "Danh mục con đã tồn tại", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(1003, "Không tìm thấy danh mục cha", HttpStatus.NOT_FOUND),
    CATEGORY_CHILD_NOT_FOUND(1004, "Không tìm thấy danh mục con", HttpStatus.NOT_FOUND),
    CATEGORY_EXISTED(1005, "Danh mục cha đã tồn tại", HttpStatus.BAD_REQUEST),
    UPLOAD_FAILED(1006,"Không thể upload ảnh lên", HttpStatus.BAD_REQUEST),
    NEWS_EXISTED(1007, "Tin tức đã tồn tại", HttpStatus.BAD_REQUEST),
    NEWS_NOT_FOUND(1008, "Tin tức không tồn tại", HttpStatus.NOT_FOUND),
    PRODUCT_NOT_FOUND(1009, "Sản phẩm không tồn tại", HttpStatus.NOT_FOUND)
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
