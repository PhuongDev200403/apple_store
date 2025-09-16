package com.nguyenvanphuong.apple_devices.dtos.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    String shippingAddress;
    String shippingMethod;
    String note;
    List<OrderItemRequest> items;
}
