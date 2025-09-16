package com.nguyenvanphuong.apple_devices.dtos.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    Long id;
    Long productVariantId;
    String productName;
    String productImage;
    String sku;
    Integer quantity;
    BigDecimal unitPrice;
    BigDecimal totalPrice;
}
