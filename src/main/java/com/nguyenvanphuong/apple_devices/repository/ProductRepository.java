package com.nguyenvanphuong.apple_devices.repository;

import com.nguyenvanphuong.apple_devices.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
}
