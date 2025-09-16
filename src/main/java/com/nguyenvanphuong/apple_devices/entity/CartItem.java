package com.nguyenvanphuong.apple_devices.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "cart_items")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant productVariant;

    @Column(name = "quantity", nullable = false)
    //@Min(value = 1, message = "Quantity must be at least 1")
    //@Max(value = 999, message = "Quantity cannot exceed 999")
    //@NotNull(message = "Quantity is required")
    private Integer quantity;

    // Lưu giá tại thời điểm thêm vào giỏ (tránh thay đổi khi ProductVariant cập nhật giá)
    @Column(name = "unit_price", precision = 10, scale = 2, nullable = false)
    //@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    //@NotNull(message = "Unit price is required")
    private BigDecimal unitPrice;

    @Column(name = "total_price", precision = 10, scale = 2, nullable = false)
    //@DecimalMin(value = "0.0", inclusive = false, message = "Total price must be greater than 0")
    //@NotNull(message = "Total price is required")
    private BigDecimal totalPrice;

    // Lưu thông tin sản phẩm tại thời điểm thêm vào giỏ
    @Column(name = "product_name", nullable = false)
    //@NotBlank(message = "Product name is required")
    private String productName;

    @Column(name = "product_image")
    private String productImage;

    @Column(name = "product_sku")
    private String productSku;

    // Trạng thái item
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CartItemStatus status = CartItemStatus.ACTIVE;

    // Thời gian tạo và cập nhật
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
