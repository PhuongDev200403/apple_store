package com.nguyenvanphuong.apple_devices.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Entity
@Table(name = "wishlist_item",
        uniqueConstraints = @UniqueConstraint(columnNames = {"wishlist_id", "product_variant_id"})) // sản phẩm thêm vào r thì không thể thêm vào nữa
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WishlistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "wishlist_id", nullable = false)
    Wishlist wishlist;

    @ManyToOne
    @JoinColumn(name = "product_variant_id", nullable = false)
    ProductVariant productVariant;

    @CreationTimestamp
    LocalDateTime addedAt;

}
