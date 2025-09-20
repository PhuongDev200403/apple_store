package com.nguyenvanphuong.apple_devices.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // SKU duy nhất cho mỗi variant
    @Column(name = "sku", unique = true, nullable = false)
    private String sku;

    String memory;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    //@Min(value = 0, message = "Quantity cannot be negative")
    //@NotNull(message = "Quantity is required")
    private Long quantity;

    // Thuộc tính cơ bản
    @Column(name = "color")
    private String color;

    @Column(name = "image_url")
    String imageUrl;

    // Thông số kỹ thuật linh hoạt (JSON)
    @Column(name = "specifications", columnDefinition = "TEXT")
    private String specifications;

    // Trạng thái variant
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProductVariantStatus status;

    // Slug cho SEO
    @Column(name = "slug")
    private String slug;

    // Thời gian tạo và cập nhật
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void updateStatus() {
        if (this.quantity != null && this.quantity <= 0) {
            this.status = ProductVariantStatus.OUT_OF_STOCK;
        } else {
            this.status = ProductVariantStatus.ACTIVE;
        }
    }
}
