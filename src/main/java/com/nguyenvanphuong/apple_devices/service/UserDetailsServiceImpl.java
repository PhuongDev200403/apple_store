package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.UserRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.UserResponse;
import com.nguyenvanphuong.apple_devices.entity.Role;
import com.nguyenvanphuong.apple_devices.entity.User;
import com.nguyenvanphuong.apple_devices.exception.AppException;
import com.nguyenvanphuong.apple_devices.exception.ErrorCode;
import com.nguyenvanphuong.apple_devices.mapper.UserMapper;
import com.nguyenvanphuong.apple_devices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    @Lazy
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =  userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().name())
                .build();
    }

    //Phương thức tạo mới một user chỉ có admin mới được tạo user kiểu này
    public UserResponse createUser(UserRequest request){
        //kiểm tra xem tên đã tồn tại hay chưa
        if(request.getEmail() == null || request.getPassword() == null) {
            throw new AppException(ErrorCode.INVALID_INPUT);
        }

        // Kiểm tra user tồn tại
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User newUser = userMapper.toUser(request);
        newUser.setCreateAt(LocalDateTime.now());
        newUser.setUpdateAt(LocalDateTime.now());
        newUser.setPhone(request.getPhone());
        //chỉ cho phép taọ user không cho phép tạo tk admin
        newUser.setRole(request.getRole());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(newUser);
        return userMapper.toUserResponse(newUser);
    }

    //Xóa user nhưng không được xóa admin
    public String deleteUser(Long id){
        //Tìm và kiểm tra xem phải admin không đã
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if(user.getRole().equals(Role.ADMIN)){
            throw new AppException(ErrorCode.ADMIN_ACC);
        }

        userRepository.deleteById(id);
        return "Xóa thành công";
    }

    //cập nhât thông tin cá nhân
    public UserResponse updateUser(Long id, UserRequest request) {
        // Tìm user cần cập nhật
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Kiểm tra xem email mới có bị trùng với user khác không (nếu email thay đổi)
        if (!existingUser.getEmail().equals(request.getEmail())) {
            Optional<User> userWithSameEmail = userRepository.findByEmail(request.getEmail());
            if (userWithSameEmail.isPresent()) {
                throw new AppException(ErrorCode.USER_EXISTED);
            }
        }

        // Cập nhật thông tin từ request
        existingUser.setEmail(request.getEmail());
        existingUser.setUsername(request.getUsername());
        existingUser.setPhone(request.getPhone());

        // Chỉ cập nhật password nếu có password mới
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // Cập nhật thời gian
        existingUser.setUpdateAt(LocalDateTime.now());

        // Lưu và trả về response
        userRepository.save(existingUser);
        return userMapper.toUserResponse(existingUser);
    }

    //Phương thức lấy tất cả user ra chỉ có admin mới làm được
    public List<UserResponse> getAllUser(){
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    //Lấy user theo id chỉ có admin mới có quyền
    public UserResponse getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toUserResponse(user);
    }

    //Lấy thông tin người dùng hiện tại
    public UserResponse getCurrentUser() {
        // Lấy thông tin authentication từ SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Lấy email từ authentication (username trong trường hợp này là email)
        String email = authentication.getName();

        // Tìm user trong database
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toUserResponse(user);
    }
}
