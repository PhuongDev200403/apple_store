package com.nguyenvanphuong.apple_devices.configurantion;

import com.nguyenvanphuong.apple_devices.entity.Role;
import com.nguyenvanphuong.apple_devices.entity.User;
import com.nguyenvanphuong.apple_devices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class InitilConfig implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
            System.out.println("Admin account created: username=admin, password=admin");
        }
    }
}
