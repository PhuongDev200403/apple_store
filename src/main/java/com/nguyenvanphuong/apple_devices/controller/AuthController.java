package com.nguyenvanphuong.apple_devices.controller;

import com.nguyenvanphuong.apple_devices.configuration.JwtUtil;
import com.nguyenvanphuong.apple_devices.dtos.request.LoginRequest;
import com.nguyenvanphuong.apple_devices.dtos.request.RegisterRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ApiResponse;
import com.nguyenvanphuong.apple_devices.dtos.response.LoginRespone;
import com.nguyenvanphuong.apple_devices.entity.Role;
import com.nguyenvanphuong.apple_devices.entity.User;
import com.nguyenvanphuong.apple_devices.exception.AppException;
import com.nguyenvanphuong.apple_devices.exception.ErrorCode;
import com.nguyenvanphuong.apple_devices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    //Endpoint public
    public ApiResponse<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String token = jwtUtil.generateToken(userDetails);
            return ApiResponse.builder()
                    .result(token)
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .result(ErrorCode.LOGIN_FAILED)
                    .build();
        }
    }

    //Phương thức đăng ký của người dùng nếu chưa có tài khoản
    @PostMapping("/register")
    public ApiResponse<?> register(@RequestBody RegisterRequest request){

        if(request.getEmail() == null || request.getUsername() == null || request.getPhone() == null || request.getPassword() == null){
            throw new AppException(ErrorCode.INVALID_INPUT);
        }

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());
        newUser.setCreateAt(LocalDateTime.now());
        newUser.setUpdateAt(LocalDateTime.now());
        newUser.setPhone(request.getPhone());

        newUser.setRole(Role.USER);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(newUser);
        return ApiResponse.builder()
                .result("Đăng ký thành công")
                .build();
    }


}
