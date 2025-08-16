package com.nguyenvanphuong.apple_devices.dtos.response;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryChildResponse {
    Long id;
    String name;
    String description;
    Long categoryId;
}
