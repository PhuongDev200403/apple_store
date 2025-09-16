package com.nguyenvanphuong.apple_devices.repository;

import com.nguyenvanphuong.apple_devices.entity.Cart;
import com.nguyenvanphuong.apple_devices.entity.CartItem;
import com.nguyenvanphuong.apple_devices.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProductVariant(Cart cart, ProductVariant productVariant);

    void deleteAllByCart_Id(Long cartId);
}
