package com.nguyenvanphuong.apple_devices.controller;

import com.nguyenvanphuong.apple_devices.dtos.request.CategoryRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ApiResponse;
import com.nguyenvanphuong.apple_devices.dtos.response.CategoryResponse;
import com.nguyenvanphuong.apple_devices.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    CategoryServiceImpl categoryService;

    //phương thức tạo danh mục mới
    @PostMapping()
    public ApiResponse<CategoryResponse> create(@RequestBody CategoryRequest request){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.creatNewCategory(request))
                .build();
    }

    //Phương thức Lấy tất cả danh mục
    @GetMapping()
    public ApiResponse<List<CategoryResponse>> getAll(){
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getAllCategory())
                .build();
    }

    //Lấy danh mục bằng id
    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable Long id){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.getCategoryById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<CategoryResponse> updateCategoryById(@RequestBody CategoryRequest request, @PathVariable Long id){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.updateCategory(request, id))
                .build();
    }

    @DeleteMapping("{id}")
    public ApiResponse<?> deleteCategoryById(@PathVariable Long id){
        categoryService.deleteCategory(id);

        return ApiResponse.builder()
                .result("Xóa thành công")
                .build();
    }

}

