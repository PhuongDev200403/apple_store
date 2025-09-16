package com.nguyenvanphuong.apple_devices.mapper;

import com.nguyenvanphuong.apple_devices.dtos.request.OrderRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.OrderResponse;
import com.nguyenvanphuong.apple_devices.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    Order toOrder(OrderRequest request);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phone", target = "phone")
    @Mapping(source = "orderDate", target = "orderDate")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "totalAmount", target = "totalAmount")
    @Mapping(source = "shippingAddress", target = "shippingAddress")
    @Mapping(source = "shippingMethod", target = "shippingMethod")
    @Mapping(source = "note", target = "note")
    @Mapping(source = "orderItems", target = "items")
    OrderResponse toResponse(Order order);
}
