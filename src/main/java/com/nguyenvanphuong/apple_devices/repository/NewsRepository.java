package com.nguyenvanphuong.apple_devices.repository;

import com.nguyenvanphuong.apple_devices.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    boolean existsByTitle(String title);
}
