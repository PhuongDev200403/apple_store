package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.configurantion.JwtUtil;
import com.nguyenvanphuong.apple_devices.dtos.request.LoginRequest;
import com.nguyenvanphuong.apple_devices.dtos.request.RegisterRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ApiResponse;
import com.nguyenvanphuong.apple_devices.dtos.response.LoginRespone;
import com.nguyenvanphuong.apple_devices.dtos.response.UserResponse;
import com.nguyenvanphuong.apple_devices.entity.Role;
import com.nguyenvanphuong.apple_devices.entity.User;
import com.nguyenvanphuong.apple_devices.exception.AppException;
import com.nguyenvanphuong.apple_devices.exception.ErrorCode;
import com.nguyenvanphuong.apple_devices.mapper.UserMapper;
import com.nguyenvanphuong.apple_devices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JavaMailSender mailSender;

    // Phương thức đăng nhập
    public String login(LoginRequest request){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            return jwtUtil.generateToken(userDetails);
        } catch (Exception e) {
            throw new AppException(ErrorCode.LOGIN_FAILED);
        }
    }

    //Phương thức đăng ký
    public UserResponse register(RegisterRequest request){

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
        //chỉ cho phép taọ user không cho phép tạo tk admin
        newUser.setRole(Role.USER);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(newUser);
        return userMapper.toUserResponse(newUser);
    }

    //Chức năng quên mật khẩu
    public void resetPasswordAndSendEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email không tồn tại"));

        // Tạo mật khẩu ngẫu nhiên (8 ký tự)
        String newPassword = UUID.randomUUID().toString().substring(0, 8);

        // Mã hóa mật khẩu và lưu vào DB
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        String subject = "Reset mật khẩu - Hệ thống của bạn";
        String text = "Xin chào " + user.getUsername() + ",\n\n"
                + "Mật khẩu mới của bạn là: " + newPassword + "\n\n"
                + "Vui lòng đăng nhập và đổi mật khẩu ngay sau khi sử dụng.";
        sendMail(user.getEmail(), subject, text);
    }

    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("phuongnguyen20403@gmail.com"); // phải trùng spring.mail.username
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
