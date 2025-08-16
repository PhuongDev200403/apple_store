package com.nguyenvanphuong.apple_devices.dtos.request;

import com.nguyenvanphuong.apple_devices.dtos.response.ProductVariantResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    String name;
    Boolean status = true;
    Long categoryChildId;
}
