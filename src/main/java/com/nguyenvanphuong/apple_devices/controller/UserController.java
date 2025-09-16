package com.nguyenvanphuong.apple_devices.controller;

import com.nguyenvanphuong.apple_devices.dtos.request.UserRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ApiResponse;
import com.nguyenvanphuong.apple_devices.dtos.response.UserResponse;
import com.nguyenvanphuong.apple_devices.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    //Phương thức tạo tài khoản của người dùng của admin
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request){
        return new ResponseEntity<>(userDetailsService.createUser(request), HttpStatus.CREATED);
    }

    //Phương thức xóa user
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<?> delete(@PathVariable Long id){
        return ApiResponse.builder()
                .result(userDetailsService.deleteUser(id))
                .build();
    }

    //Phương thức cập nhật tài khoản
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.username")
    public ApiResponse<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userDetailsService.updateUser(id, request))
                .build();
    }

    //Phương thức lấy danh sách user của admin
    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<List<UserResponse>> getAll(){
        return ApiResponse.<List<UserResponse>>builder()
                .result(userDetailsService.getAllUser())
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    //Phương thức lấy user theo id của admin
    public ApiResponse<UserResponse> getUserById(@PathVariable Long id){
        return ApiResponse.<UserResponse>builder()
                .result(userDetailsService.getUserById(id))
                .build();
    }

    //Phương thức lấy user Hiện tại
    @GetMapping("/my_infor")
    public ApiResponse<UserResponse> getCurrentUser(){
        return ApiResponse.<UserResponse>builder()
                .result(userDetailsService.getCurrentUser())
                .build();
    }
}
