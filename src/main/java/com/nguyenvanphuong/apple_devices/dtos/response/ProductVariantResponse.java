package com.nguyenvanphuong.apple_devices.dtos.response;

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
public class ProductVariantResponse {
    Long id;
    float price;
    boolean status = true;
    String imageUrl;
    Long quantity;
    Long memory;
    String color;
    //Long product_id;
}
