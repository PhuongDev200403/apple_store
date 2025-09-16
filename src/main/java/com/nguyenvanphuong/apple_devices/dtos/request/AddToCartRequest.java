package com.nguyenvanphuong.apple_devices.dtos.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddToCartRequest {
    Long productVariantId;
    Integer quantity;
}
