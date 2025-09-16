package com.nguyenvanphuong.apple_devices.dtos.response;

import com.nguyenvanphuong.apple_devices.entity.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Long id;
    String name;
    ProductStatus status;
    Long categoryChildId;
    //List<ProductVariantResponse> productVariants = new ArrayList<>();
}

