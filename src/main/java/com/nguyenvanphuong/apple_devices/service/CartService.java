package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.AddToCartRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.CartResponse;

public interface CartService {

    //Phướng thức thêm sản phẩm vào giỏ hàng
    CartResponse addToCart(AddToCartRequest request);
    //Phương thức xem giỏ hàng theo id
    CartResponse getCartByUserId(Long userId);
    //Phương thức xóa sản phẩm trong giỏ hàng
    CartResponse deleteCartItem(Long productVariantId);
    //Phương thức xóa hết sản phẩm trong giỏ hàng
    CartResponse clearCart(Long targetUserId);
    //Phương thức lấy cart của bản thân
    CartResponse getMyCart();
}
