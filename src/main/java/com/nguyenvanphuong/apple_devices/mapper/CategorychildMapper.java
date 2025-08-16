package com.nguyenvanphuong.apple_devices.mapper;

import com.nguyenvanphuong.apple_devices.dtos.request.CategoryChildRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.CategoryChildResponse;
import com.nguyenvanphuong.apple_devices.entity.CategoryChild;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategorychildMapper {
    @Mapping(target = "category", ignore = true)
    CategoryChild toCategoryChild(CategoryChildRequest request);

    @Mapping(source = "category.id", target = "categoryId")
    CategoryChildResponse toCategoryChildResponse(CategoryChild categoryChild);
}
