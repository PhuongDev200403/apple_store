package com.nguyenvanphuong.apple_devices.controller;

import com.nguyenvanphuong.apple_devices.dtos.request.CategoryChildRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ApiResponse;
import com.nguyenvanphuong.apple_devices.dtos.response.CategoryChildResponse;
import com.nguyenvanphuong.apple_devices.service.CategoryChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/series")
public class CategoryChildController {
    @Autowired
    CategoryChildService categoryChildService;

    //Phương thức Thêm mới một danh mục con chỉ có admin mới được thao tác
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<CategoryChildResponse> create(@RequestBody CategoryChildRequest request){
        return ApiResponse.<CategoryChildResponse>builder()
                .result(categoryChildService.createNewCateChild(request))
                .build();
    }

    //Phương thứ lấy tất cả danh sách
    //Endpoint public
    @GetMapping()
    public ApiResponse<List<CategoryChildResponse>> getAll(){
        return ApiResponse.<List<CategoryChildResponse>>builder()
                .result(categoryChildService.getAllCateChild())
                .build();
    }

    //Phương thức lấy danh mục con theo id
    //Endpoint public
    @GetMapping("/{id}")
    public ApiResponse<CategoryChildResponse> getCateChildById(@PathVariable Long id){
        return ApiResponse.<CategoryChildResponse>builder()
                .result(categoryChildService.getCateChildById(id))
                .build();
    }

    //Phương thức cập nhật danh mục chỉ có admin
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<CategoryChildResponse> updateCateChild(@RequestBody CategoryChildRequest request, @PathVariable Long id){
        return ApiResponse.<CategoryChildResponse>builder()
                .result(categoryChildService.updateCateChild(request, id))
                .build();
    }

    //Phương thức xóa danh mục con
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<String> deleteCateChildById(@PathVariable Long id){
        categoryChildService.deleteCateChild(id);
        return ApiResponse.<String>builder()
                .result("Xóa thành công")
                .build();
    }

    //Phương thức lấy danh mục con theo danh mục cha
    //Endpoint public
    @GetMapping("/category/{id}")
    public ApiResponse<List<CategoryChildResponse>> getByCategory(@PathVariable Long id){
        return ApiResponse.<List<CategoryChildResponse>>builder()
                .result(categoryChildService.getCateChileByCategoryId(id))
                .build();
    }
}
