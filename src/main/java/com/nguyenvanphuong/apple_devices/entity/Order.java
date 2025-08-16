package com.nguyenvanphuong.apple_devices.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Thời gian đặt hàng
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    // Trạng thái: PENDING, PAID, SHIPPED, COMPLETED, CANCELLED
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    // Tổng tiền
    @Column(name = "total_amount")
    private float totalAmount;

    // Địa chỉ giao hàng
    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "shipping_method")
    String shippingMethod;

    // Ghi chú
    private String note;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
}
