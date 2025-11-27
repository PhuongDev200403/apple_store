package com.nguyenvanphuong.apple_devices.repository;

import com.nguyenvanphuong.apple_devices.entity.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    List<Product> findByStatusTrue();

    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN FETCH p.productVariants pv " +
            "WHERE p.status = 'ACTIVE'")
    List<Product> findAllActiveWithVariants();

    //tìm kiếm theo tên
    @Query(value = "SELECT * FROM products WHERE name LIKE %:keyword%", nativeQuery = true)
    List<Product> searchByNameNative(@Param("keyword") String keyword);

    List<Product> findByCategoryChildId(Long id);

}
