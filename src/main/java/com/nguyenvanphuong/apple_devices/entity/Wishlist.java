package com.nguyenvanphuong.apple_devices.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
//Một người chỉ có thể add một lần cho một sản phẩm
@Table(name = "wishlist", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "product_id"}))
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @Column(name = "created_at")
    LocalDateTime createdAt;
}
