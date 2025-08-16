package com.nguyenvanphuong.apple_devices.dtos.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
    Long id;
    String name;
    String description;
    List<CategoryChildResponse> series;
}
//trả về danh sách các danh mục con