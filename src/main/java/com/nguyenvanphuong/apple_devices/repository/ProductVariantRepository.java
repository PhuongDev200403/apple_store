package com.nguyenvanphuong.apple_devices.repository;

import com.nguyenvanphuong.apple_devices.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    Optional<ProductVariant> findByProductIdAndColorAndMemory(Long productId, String color, String memory);
    boolean existsBySku(String sku);

    List<ProductVariant> findByProductId(Long id);

    @Query("SELECT pv FROM ProductVariant pv JOIN FETCH pv.product WHERE pv.id = :id")
    Optional<ProductVariant> findByIdWithProduct(@Param("id") Long id);

}
