package com.nguyenvanphuong.apple_devices.mapper;

import com.nguyenvanphuong.apple_devices.dtos.request.AddToWishlistRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.WishlistResponse;
import com.nguyenvanphuong.apple_devices.entity.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {WishlistItemMapper.class})
public interface WishlistMapper {
    //Wishlist toWishlist(AddToWishListRequest request);
    @Mapping(source = "user.id", target = "userId")
    WishlistResponse toResponse(Wishlist wishlist);
}
