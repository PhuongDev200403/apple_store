package com.nguyenvanphuong.apple_devices.controller;

import com.nguyenvanphuong.apple_devices.dtos.request.LoginRequest;
import com.nguyenvanphuong.apple_devices.dtos.request.RegisterRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ApiResponse;
import com.nguyenvanphuong.apple_devices.service.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;


    @PostMapping("/login")
    //Endpoint public
    public ApiResponse<String> login(@RequestBody LoginRequest request) {
        return ApiResponse.<String>builder()
                .result(authService.login(request))
                .build();
    }

    //Phương thức đăng ký của người dùng nếu chưa có tài khoản
    @PostMapping("/register")
    public ApiResponse<?> register(@RequestBody RegisterRequest request){
        return ApiResponse.builder()
                .result(authService.register(request))
                .build();
    }

    @PostMapping("/forgot-password")
    public ApiResponse<String> forgotPassword(@RequestParam String email) {
        authService.resetPasswordAndSendEmail(email);
        return ApiResponse.<String>builder()
                .result("Mật khẩu mới đã được gửi đến mail của bạn")
                .build();
    }

}
