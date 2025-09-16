package com.nguyenvanphuong.apple_devices.mapper;

import com.nguyenvanphuong.apple_devices.dtos.response.WishlistItemResponse;
import com.nguyenvanphuong.apple_devices.entity.WishlistItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductVariantMapper.class})
public interface WishlistItemMapper {
    @Mapping(source = "productVariant", target = "productVariant")
    WishlistItemResponse toResponse(WishlistItem wishlistItem);
}
