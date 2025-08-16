package com.nguyenvanphuong.apple_devices.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "create_at")
    LocalDateTime createAt;

    @Column(name = "update_at")
    LocalDateTime updateAt;

    @Column(name = "status")
    Boolean status = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_child_id", nullable = false)
    CategoryChild categoryChild;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductVariant> productVariants = new ArrayList<>();
}
