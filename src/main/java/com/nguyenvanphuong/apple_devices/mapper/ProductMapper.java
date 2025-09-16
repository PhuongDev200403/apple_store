package com.nguyenvanphuong.apple_devices.mapper;

import com.nguyenvanphuong.apple_devices.dtos.request.ProductRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ProductResponse;
import com.nguyenvanphuong.apple_devices.entity.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "productVariants", ignore = true)
    @Mapping(target = "categoryChild", ignore = true)// sẽ set thủ công trong service
    Product toEntity(ProductRequest request);

    @Mapping(source = "categoryChild.id", target = "categoryChildId")
    ProductResponse toResponse(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(ProductRequest request, @MappingTarget Product product);
}
