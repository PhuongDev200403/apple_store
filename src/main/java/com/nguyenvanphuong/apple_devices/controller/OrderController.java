package com.nguyenvanphuong.apple_devices.controller;

import com.nguyenvanphuong.apple_devices.dtos.request.CheckOutResquest;
import com.nguyenvanphuong.apple_devices.dtos.request.OrderRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ApiResponse;
import com.nguyenvanphuong.apple_devices.dtos.response.OrderResponse;
import com.nguyenvanphuong.apple_devices.entity.OrderStatus;
import com.nguyenvanphuong.apple_devices.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    //Tạo đơn hàng khi thanh toán ngay
    @PostMapping()
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest request){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(request))
                .build();
    }
    //Tạo đơn hàng khi thanh toán trong giỏ hàng
    @PostMapping("/check_out")
    public ApiResponse<OrderResponse> checkOutCart(@RequestBody CheckOutResquest request){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.checkOutCart(request))
                .build();
    }

    //Lấy tất cả đơn hàng chỉ có admin mới lấy được nhé
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<List<OrderResponse>> getAll(){
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getAllOrder())
                .build();
    }

    //Phương thức lấy đơn hàng theo id
    //Phải đăng nhập mới thao tác được
    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getOrderById(@PathVariable Long id){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.getById(id))
                .build();
    }

    //Phương thức lấy order của bản thân
    @GetMapping("/my_orders")
    public ApiResponse<List<OrderResponse>> getMyOrders(){
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getMyOrders())
                .build();
    }

    //Phương thức cập nhật trạng thái đơn hàng
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<OrderResponse> updateStatus(@PathVariable Long id,
                                                   @RequestParam OrderStatus newStatus)
    {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.updateStatus(id, newStatus))
                .build();
    }
}
