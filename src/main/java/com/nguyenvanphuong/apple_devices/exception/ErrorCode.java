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
    PRODUCT_NOT_FOUND(1009, "Sản phẩm không tồn tại", HttpStatus.NOT_FOUND),
    PRODUCT_EXISTED(1010, "Sản phẩm đã tại", HttpStatus.BAD_REQUEST),
    ADMIN_ACC(1011, "Tài khoản admin không ther xóa", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1012, "Không tim thấy tài khoản cần xóa", HttpStatus.NOT_FOUND),
    INVALID_INPUT(1013, "Các trường dữ liệu không được để trống", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(9998, "Không có quyền truy cập", HttpStatus.UNAUTHORIZED),
    PRODUCT_VARIANT_NOT_FOUND(1014, "Không tìm thấy sản phẩm cần thêm vào giỏ hàng", HttpStatus.NOT_FOUND),
    SKU_EXISTED(1015, "Sku đã tồn tại", HttpStatus.BAD_REQUEST),
    INVALID_QUANTITY(1016, "Số lượng không hợp lệ", HttpStatus.BAD_REQUEST),
    FAILED_TO_ADD_TO_CART(1017, "Sản phẩm đang hết hàng không thể thêm", HttpStatus.BAD_REQUEST),
    CART_NOT_FOUND(1018, "Không tìm thây giỏ hàng", HttpStatus.NOT_FOUND),
    CART_EMPTY(1019, "Giỏ hàng rỗng không thể tiến hành thanh toán", HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND(1020, "Đơn hàng không tồn tại", HttpStatus.NOT_FOUND),
    ORDER_ALREADY_COMPLETED(1021, "Đơn hàng đã được hoàn thành không thể cập nhật trạng thái", HttpStatus.BAD_REQUEST),
    ORDER_ALREADY_CANCELLED(1022, "Đơn hàng đã bị hủy", HttpStatus.BAD_REQUEST),
    ITEM_ALREADY_EXISTS(1023, "Sản phẩm đã được thêm vào danh sách yêu thích r không thêm nữa", HttpStatus.BAD_REQUEST),
    WISHLIST_NOT_FOUND(1024, "Danh sách yêu thích không tồn tại", HttpStatus.NOT_FOUND),
    ITEM_NOT_FOUND(1025, "Không tìm thấy sản phẩm yêu thích cần xóa", HttpStatus.NOT_FOUND),
    FILE_UPLOAD_FAILED(1026, "Lỗi xảy ra trong quá trình upload ảnh", HttpStatus.BAD_REQUEST),
    CART_ITEM_NOT_FOUND(1027, "Không tìm thấy sản phẩm cần xóa ttrong giỏ hàng", HttpStatus.NOT_FOUND),
    PRODUCT_OUT_OF_STOCK(1028, "Sản phẩm đã hết hàng trong kho", HttpStatus.BAD_REQUEST)


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
