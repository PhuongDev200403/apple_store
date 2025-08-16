package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.UserRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.UserResponse;
import com.nguyenvanphuong.apple_devices.entity.Role;
import com.nguyenvanphuong.apple_devices.entity.User;
import com.nguyenvanphuong.apple_devices.mapper.UserMapper;
import com.nguyenvanphuong.apple_devices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    //Phương thức tạo mới một user
    public UserResponse createUser(UserRequest request){
        //kiểm tra xem tên đã tồn tại hay chưa
        Optional<User> user = userRepository.findByUsername(request.getUsername());

        if (user.isPresent()){
            throw new RuntimeException("Tên người dùng đã tồn tại");
        }
        User newUser = userMapper.toUser(request);
        newUser.setCreateAt(LocalDateTime.now());
        newUser.setUpdateAt(LocalDateTime.now());
        newUser.setRole(Role.USER);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(newUser);
        return userMapper.toUserResponse(newUser);
    }

    //Xóa user nhưng không được xóa admin
    public String deleteUser(Long id){
        //Tìm và kiểm tra xem phải admin không đã
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent() && user.get().getUsername().equals("admin")){
            throw new RuntimeException("Tài khoản admin không thể xóa");
        }
        userRepository.deleteById(id);
        return "Xóa thành công";
    }
}
