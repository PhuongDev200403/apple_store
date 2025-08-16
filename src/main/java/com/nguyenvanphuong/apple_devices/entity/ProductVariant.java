package com.nguyenvanphuong.apple_devices.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "product_variant")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "price")
    float price;

    @Column(name = "status")
    boolean status = true;

    @Column(name = "image_url")
    String imageUrl;

    @Column(name = "quantity")
    Long quantity;

    @Column(name = "memory")
    Long memory;

    @Column(name = "color")
    String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    Product product;
}
