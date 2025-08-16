package com.nguyenvanphuong.apple_devices.mapper;

import com.nguyenvanphuong.apple_devices.dtos.request.ProductVariantRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ProductVariantResponse;
import com.nguyenvanphuong.apple_devices.entity.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper {
    @Mapping(target = "imageUrl", ignore = true) // sẽ set trong service
    @Mapping(target = "product", ignore = true)
    ProductVariant toEntity(ProductVariantRequest request);

    ProductVariantResponse toResponse(ProductVariant variant);
}
