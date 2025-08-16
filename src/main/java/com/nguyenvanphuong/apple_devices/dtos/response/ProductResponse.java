package com.nguyenvanphuong.apple_devices.dtos.response;

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
    Boolean status;
    Long categoryChildId;
    List<ProductVariantResponse> productVariants = new ArrayList<>();
}

