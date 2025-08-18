package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.CategoryChildRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.CategoryChildResponse;

import java.util.List;

public interface CategoryChildService {

    //Tạo mới một danh mục con
    CategoryChildResponse createNewCateChild(CategoryChildRequest request);

    //Cập nhật một danh mục con
    CategoryChildResponse updateCateChild(CategoryChildRequest request, Long id);

    //Lấy danh sách tất cả danh mục
    List<CategoryChildResponse> getAllCateChild();

    //Xóa một danh mục con
    void deleteCateChild(Long id);

    //Lấy danh mục theo id của danh mục
    CategoryChildResponse getCateChildById(Long id);

    //Lấy danh mục con bằng id của danh mục cha
    List<CategoryChildResponse> getCateChileByCategoryId(Long id);
}
