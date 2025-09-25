package com.nguyenvanphuong.apple_devices.controller;

import com.nguyenvanphuong.apple_devices.dtos.request.AddToCartRequest;
import com.nguyenvanphuong.apple_devices.dtos.request.UpdateQuantityRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ApiResponse;
import com.nguyenvanphuong.apple_devices.dtos.response.CartResponse;
import com.nguyenvanphuong.apple_devices.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //Phương thức lấy giỏ hàng theo id người dùng
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<CartResponse> getCartByUserId(@PathVariable("id") Long userId){
        return ApiResponse.<CartResponse>builder()
                .result(cartService.getCartByUserId(userId))
                .build();
    }

    //Phương thức lấy giỏ hàng của bản thân
    @GetMapping("/my-cart")
    public ApiResponse<CartResponse> getMyCart(){
        return ApiResponse.<CartResponse>builder()
                .result(cartService.getMyCart())
                .build();
    }

    //Phương thức clear giỏ hàng của bản thân yêu cầu đăng nhập
    @PutMapping("/clear")
    public ApiResponse<CartResponse> clearCart(@RequestParam(required = false) Long userId) {

        return ApiResponse.<CartResponse>builder()
                .result(cartService.clearCart(userId))
                .build();
    }

    //Phương thức xóa sản phẩm trong giỏ hàng
    @DeleteMapping("/{id}")
    public ApiResponse<CartResponse> deleteItemFromCart(@PathVariable("id") Long productVariantId){
        return ApiResponse.<CartResponse>builder()
                .result(cartService.deleteCartItem(productVariantId))
                .build();
    }

    //Phương thức lấy tất cả danh sách giỏ hàng
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping()
    public ApiResponse<List<CartResponse>> getAll(){
        return ApiResponse.<List<CartResponse>>builder()
                .result(cartService.getAllCart())
                .build();
    }

    //Phương thức cập nhật số lượng của một sản phẩm trong giỏ hàng
    @PutMapping("/{id}")
    public ApiResponse<CartResponse> updateQuantity(@PathVariable("id") Long variantId, @RequestBody UpdateQuantityRequest request){
        return ApiResponse.<CartResponse>builder()
                .result(cartService.UpdateQuantity(variantId, request))
                .build();
    }
}
