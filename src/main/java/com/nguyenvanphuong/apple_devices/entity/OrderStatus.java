package com.nguyenvanphuong.apple_devices.entity;

public enum OrderStatus {
    PENDING,      // Đang chờ xử lý
    PAID,         // Đã thanh toán
    SHIPPED,      // Đã giao cho đơn vị vận chuyển
    COMPLETED,    // Hoàn tất
    CANCELLED
}
