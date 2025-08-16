package com.nguyenvanphuong.apple_devices.controller;

import com.nguyenvanphuong.apple_devices.dtos.request.CategoryChildRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.CategoryChildResponse;
import com.nguyenvanphuong.apple_devices.service.CategoryChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/series")
public class CategoryChildController {
    @Autowired
    CategoryChildService categoryChildService;

    //Phương thức Thêm mới một danh mục con
    @PostMapping()
    public ResponseEntity<CategoryChildResponse> create(@RequestBody CategoryChildRequest request){
        return new ResponseEntity<>(categoryChildService.createNewCateChild(request), HttpStatus.CREATED);
    }

    //Phương thứ lấy tất cả danh sách
    @GetMapping()
    public ResponseEntity<List<CategoryChildResponse>> getAll(){
        return new ResponseEntity<>(categoryChildService.getAllCateChild(), HttpStatus.OK);
    }

    //Phương thức lấy danh mục con theo id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryChildResponse> getCateChildById(@PathVariable Long id){
        return new ResponseEntity<>(categoryChildService.getCateChildById(id), HttpStatus.OK);
    }

    //Phương thức cập nhật danh mục
    @PutMapping("/{id}")
    public ResponseEntity<CategoryChildResponse> updateCateChild(@RequestBody CategoryChildRequest request, @PathVariable Long id){
        return new ResponseEntity<>(categoryChildService.updateCateChild(request, id), HttpStatus.OK);
    }

    //Phương thức xóa danh mục con
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCateChildById(@PathVariable Long id){
        return new ResponseEntity<>(categoryChildService.deleteCateChild(id), HttpStatus.OK);
    }

    //Phương thức lấy danh mục con theo danh mục cha
    @GetMapping("/category/{id}")
    public ResponseEntity<List<CategoryChildResponse>> getByCategory(@PathVariable Long id){
        return new ResponseEntity<>(categoryChildService.getCateChileByCategoryId(id), HttpStatus.OK);
    }
}
