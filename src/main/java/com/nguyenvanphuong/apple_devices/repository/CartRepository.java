package com.nguyenvanphuong.apple_devices.repository;

import com.nguyenvanphuong.apple_devices.entity.Cart;
import com.nguyenvanphuong.apple_devices.entity.CartStatus;
import com.nguyenvanphuong.apple_devices.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserAndStatus(User user, CartStatus status);
    Optional<Cart> findByUser(User user);
}
