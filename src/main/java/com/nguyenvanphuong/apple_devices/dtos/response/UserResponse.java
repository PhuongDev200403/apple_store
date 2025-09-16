package com.nguyenvanphuong.apple_devices.dtos.response;

import com.nguyenvanphuong.apple_devices.entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String username;
    String phone;
    String email;
    Role role;
    LocalDateTime createAt;
    LocalDateTime updateAt;
}
