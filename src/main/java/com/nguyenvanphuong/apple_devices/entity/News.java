package com.nguyenvanphuong.apple_devices.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "news")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "title")
    String title;
    @Column(name = "content")
    String content;
    @Column(name = "image_url")
    String imageUrl;
    @Column(name = "is_featured")
    Boolean isFeatured;
    @Column(name = "posted_date")
    LocalDateTime publishedAt;
    @Column(name = "is_active")
    Boolean isActive;
}
