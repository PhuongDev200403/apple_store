package com.nguyenvanphuong.apple_devices.mapper;

import com.nguyenvanphuong.apple_devices.dtos.response.OrderItemResponse;
import com.nguyenvanphuong.apple_devices.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(source = "productVariant.id", target = "productVariantId")
    @Mapping(source = "productVariant.product.name", target = "productName")
    OrderItemResponse toResponse(OrderItem orderItem);
}
