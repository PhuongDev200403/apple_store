package com.nguyenvanphuong.apple_devices.mapper;

import com.nguyenvanphuong.apple_devices.dtos.response.CartItemResponse;
import com.nguyenvanphuong.apple_devices.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(source = "productVariant.id", target = "productVariantId")
    @Mapping(source = "productName", target = "productName")
    @Mapping(source = "productImage", target = "productImage")
    @Mapping(source = "productSku", target = "sku")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "unitPrice", target = "unitPrice")
    @Mapping(source = "totalPrice", target = "totalPrice")
    CartItemResponse toResponse(CartItem cartItem);
}
