package com.nguyenvanphuong.apple_devices.dtos.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantResponse {
    Long id;
    float price;
    boolean status = true;
    String imageUrl;
    Long quantity;
    Long memory;
    String color;
    float screenSize;
    String resolution;
    String cpu;
    String chipset;
    String screenType;
    //Long product_id;
}
