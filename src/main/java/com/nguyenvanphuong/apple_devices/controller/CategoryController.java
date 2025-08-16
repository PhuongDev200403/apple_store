package com.nguyenvanphuong.apple_devices.controller;

import com.nguyenvanphuong.apple_devices.dtos.request.CategoryRequest;
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
    public ResponseEntity<?> create(@RequestBody CategoryRequest request){
        boolean exists = categoryService.existed(request.getName());
        if(exists){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Danh mục đã tồn tại");
        }
        return new ResponseEntity<>(categoryService.creatNewCategory(request), HttpStatus.CREATED);
    }

    //Phương thức Lấy tất cả danh mục
    @GetMapping()
    public ResponseEntity<List<CategoryResponse>> getAll(){
        return new ResponseEntity<>(categoryService.getAllCategory(), HttpStatus.OK);
    }

    //Lấy danh mục bằng id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategoryById(@RequestBody CategoryRequest request, @PathVariable Long id){
        return new ResponseEntity<>(categoryService.updateCategory(request, id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.deleteCategory(id), HttpStatus.OK);
    }

}

