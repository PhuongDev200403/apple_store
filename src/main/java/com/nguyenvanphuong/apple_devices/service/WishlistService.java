package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.AddToWishlistRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.WishlistResponse;

import java.util.List;

public interface WishlistService {
    //Phương thức thêm sản phẩm vào danh sach yêu thích
    WishlistResponse addToWishlist(AddToWishlistRequest request);

    //Phương thức lấy danh sach yêu thích của bản thân
    WishlistResponse getMyWishlist();

    //Phương thức lấy tất cả danh sách yêu thích
    List<WishlistResponse> getAllWishlist();

    //Phương thức lấy danh sách yêu thích theo userId
    WishlistResponse getByUserId(Long userId);

    //Phương thức xóa sản phẩm khỏi danh sách yêu thích
    WishlistResponse deleteWishlistItemFromWishlist(Long productVariantId);

    //Phương thức clear toàn bộ sản phẩm trong danh sách yêu thích
    //admin không có quyền clear
    WishlistResponse clearWishlist();

}
