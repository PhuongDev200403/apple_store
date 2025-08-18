package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.CategoryRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    //Tạo mơi môt danh mục cha
    CategoryResponse creatNewCategory(CategoryRequest request);
    //Lấy tất cả danh sách danh mục
    List<CategoryResponse> getAllCategory();
    //Lấy danh mục theo id
    CategoryResponse getCategoryById(Long id);
    //Cập nhât danh mục
    CategoryResponse updateCategory(CategoryRequest request, Long id);
    //Xóa Danh mục
    void deleteCategory(Long id);
    //Kiểm tra xem đã tồn tại danh mục hay chưa

    Boolean existed(String name);
}
