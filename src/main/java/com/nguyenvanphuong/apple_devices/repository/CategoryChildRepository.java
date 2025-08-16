package com.nguyenvanphuong.apple_devices.repository;

import com.nguyenvanphuong.apple_devices.entity.CategoryChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryChildRepository extends JpaRepository<CategoryChild, Long> {
    List<CategoryChild> findAllByCategoryId(Long id);
    Optional<CategoryChild> findByName(String username);
}
