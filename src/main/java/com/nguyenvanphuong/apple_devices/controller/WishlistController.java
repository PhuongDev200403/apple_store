package com.nguyenvanphuong.apple_devices.controller;

import com.nguyenvanphuong.apple_devices.dtos.request.AddToWishlistRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ApiResponse;
import com.nguyenvanphuong.apple_devices.dtos.response.WishlistResponse;
import com.nguyenvanphuong.apple_devices.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    WishlistService wishlistService;

    @PostMapping
    public ApiResponse<WishlistResponse> addToWishlist(@RequestBody AddToWishlistRequest request){
        return ApiResponse.<WishlistResponse>builder()
                .result(wishlistService.addToWishlist(request))
                .build();
    }

    //Phương thức lấy danh sách yêu thích của bản thân yêu cầu đăng nhập
    @GetMapping("/my-wishlist")
    public ApiResponse<WishlistResponse> getMyWishlist(){
        return ApiResponse.<WishlistResponse>builder()
                .result(wishlistService.getMyWishlist())
                .build();
    }

    //Phương thức lấy tất cả danh sách yêu thích
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ApiResponse<List<WishlistResponse>> getAll(){
        return ApiResponse.<List<WishlistResponse>>builder()
                .result(wishlistService.getAllWishlist())
                .build();
    }

    //Phương thức lấy danh sách yêu thích theo userId
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<WishlistResponse> getByUserId(@PathVariable Long userId){
        return ApiResponse.<WishlistResponse>builder()
                .result(wishlistService.getByUserId(userId))
                .build();
    }

    //phương thức xóa sản phẩm trong đanh sách yêu thích yêu cầu đăng nhập
    @DeleteMapping("/my-wishlist/{id}")
    public ApiResponse<WishlistResponse> deleteItem(@PathVariable("id") Long productVariantId){
        return ApiResponse.<WishlistResponse>builder()
                .result(wishlistService.deleteWishlistItemFromWishlist(productVariantId))
                .build();
    }

    //Phương thức clear sản phẩm trong danh sach yêu thích
    @PutMapping("/my-wishlist/clear")
    public ApiResponse<WishlistResponse> clear(){
        return ApiResponse.<WishlistResponse>builder()
                .result(wishlistService.clearWishlist())
                .build();
    }
}
