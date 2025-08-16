package com.nguyenvanphuong.apple_devices.mapper;

import com.nguyenvanphuong.apple_devices.dtos.request.CategoryRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.CategoryResponse;
import com.nguyenvanphuong.apple_devices.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);

    CategoryResponse toCategoryResponse(Category category);
}
