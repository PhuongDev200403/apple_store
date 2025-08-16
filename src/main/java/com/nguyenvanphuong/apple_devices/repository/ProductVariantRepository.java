package com.nguyenvanphuong.apple_devices.repository;

import com.nguyenvanphuong.apple_devices.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
}
