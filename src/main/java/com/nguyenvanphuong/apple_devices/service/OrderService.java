package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.CheckOutResquest;
import com.nguyenvanphuong.apple_devices.dtos.request.OrderRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.OrderResponse;
import com.nguyenvanphuong.apple_devices.entity.OrderStatus;

import java.util.List;

public interface OrderService {

    //Phương thức tạo đơn hàng khi mua ngay một sản phẩm
    OrderResponse createOrder(OrderRequest request);

    //Phương thức tạo đơn hàng khi thanh toán trong giỏ hàng
    OrderResponse checkOutCart(CheckOutResquest request);

    //Phương thức lấy tất cả đơn hàng
    List<OrderResponse> getAllOrder();

    //Phương thức lấy giỏ hàng theo id
    OrderResponse getById(Long id);

    //Lấy danh sách đơn hàng theo userId
    List<OrderResponse> getMyOrders();

    //Cập nhật trạng thái đơn hàng
    OrderResponse updateStatus(Long id, OrderStatus newStatus);
}
