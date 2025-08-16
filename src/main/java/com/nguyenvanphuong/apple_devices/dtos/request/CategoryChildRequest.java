package com.nguyenvanphuong.apple_devices.dtos.request;

import com.nguyenvanphuong.apple_devices.dtos.response.ProductResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryChildRequest {
    String name;
    String description;
    Long categoryId;
}
