package com.nguyenvanphuong.apple_devices.mapper;

import com.nguyenvanphuong.apple_devices.dtos.response.CartItemResponse;
import com.nguyenvanphuong.apple_devices.dtos.response.CartResponse;
import com.nguyenvanphuong.apple_devices.entity.Cart;
import com.nguyenvanphuong.apple_devices.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "totalQuantity", target = "totalQuantity")
    @Mapping(source = "items", target = "items")
    CartResponse toResponse(Cart cart);
}