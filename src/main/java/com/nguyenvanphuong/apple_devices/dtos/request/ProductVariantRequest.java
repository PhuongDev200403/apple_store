package com.nguyenvanphuong.apple_devices.dtos.request;

import com.nguyenvanphuong.apple_devices.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantRequest {
    float price;
    boolean status = true;
    MultipartFile imageUrl;
    Long quantity;
    String color;
    Long memory;
    float screenSize;
    String resolution;
    String cpu;
    String chipset;
    String screenType;
    Long productId;
}
