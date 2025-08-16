package com.nguyenvanphuong.apple_devices.repository;

import com.nguyenvanphuong.apple_devices.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
