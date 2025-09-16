package com.nguyenvanphuong.apple_devices.mapper;

import com.nguyenvanphuong.apple_devices.dtos.request.ProductVariantRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ProductVariantResponse;
import com.nguyenvanphuong.apple_devices.entity.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "status", expression = "java(com.nguyenvanphuong.apple_devices.entity.ProductVariantStatus.ACTIVE)")
    ProductVariant toEntity(ProductVariantRequest request);

    @Mapping(target = "status", expression = "java(entity.getStatus().name())")
    @Mapping(source = "product.id", target = "productId")
    ProductVariantResponse toResponse(ProductVariant entity);
}
