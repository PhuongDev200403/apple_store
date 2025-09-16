package com.nguyenvanphuong.apple_devices.controller;

import com.nguyenvanphuong.apple_devices.dtos.request.AddToCartRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ApiResponse;
import com.nguyenvanphuong.apple_devices.dtos.response.CartResponse;
import com.nguyenvanphuong.apple_devices.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    CartService cartService;

    //Phương thức thêm sản phẩm mới vào giỏ hàng phải đăng nhập
    @PostMapping()
    public ApiResponse<CartResponse> addToCart(@RequestBody AddToCartRequest request){
        System.out.println("Thêm vào giỏ hàng đã được gọi");
        return ApiResponse.<CartResponse>builder()
                .result(cartService.addToCart(request))
                .build();
    }

    //Phương thức lấy giỏ hàng của bản thân
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<CartResponse> getCartByUserId(@PathVariable("id") Long userId){
        return ApiResponse.<CartResponse>builder()
                .result(cartService.getCartByUserId(userId))
                .build();
    }

    //Phương thức clear giỏ hàng của bản thân yêu cầu đăng nhập
    @PutMapping("/clear")
    public ApiResponse<CartResponse> clearCart(@RequestParam(required = false) Long userId) {

        return ApiResponse.<CartResponse>builder()
                .result(cartService.clearCart(userId))
                .build();
    }
}
