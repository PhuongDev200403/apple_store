package com.nguyenvanphuong.apple_devices.dtos.response;

import com.nguyenvanphuong.apple_devices.entity.ProductVariantStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantResponse {
    Long id;
    BigDecimal price;
    String status;
    String imageUrl;
    Long quantity;
    String memory;
    String sku;
    String color;
    String specifications;
    String slug;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Long productId;
}
