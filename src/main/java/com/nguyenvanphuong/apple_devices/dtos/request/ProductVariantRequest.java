package com.nguyenvanphuong.apple_devices.dtos.request;

import com.nguyenvanphuong.apple_devices.entity.Product;
import com.nguyenvanphuong.apple_devices.entity.ProductVariantStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantRequest {
    Long productId;

    String sku;

    BigDecimal price;

    String memory;

    Long quantity;

    String color;

    MultipartFile imageUrl;

    String specifications;

    // Slug cho SEO
    String slug;

}
