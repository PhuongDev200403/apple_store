package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.CheckOutResquest;
import com.nguyenvanphuong.apple_devices.dtos.request.OrderItemRequest;
import com.nguyenvanphuong.apple_devices.dtos.request.OrderRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.OrderResponse;
import com.nguyenvanphuong.apple_devices.entity.*;
import com.nguyenvanphuong.apple_devices.exception.AppException;
import com.nguyenvanphuong.apple_devices.exception.ErrorCode;
import com.nguyenvanphuong.apple_devices.mapper.OrderItemMapper;
import com.nguyenvanphuong.apple_devices.mapper.OrderMapper;
import com.nguyenvanphuong.apple_devices.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CartRepository cartRepository;
    //private final CartItemRepository cartItemRepository;
    private final CartService cartService;

    //Phương thức tạo đơn hàng khi người dùng click mua hàng
    @Override
    public OrderResponse createOrder(OrderRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            System.out.println("Bạn chưa đăng nhập đâu nhé, hãy đăng nhập nhanh chóng để có thể mua hàng");
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Order order = orderMapper.toOrder(request);
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        if (order.getOrderItems() == null) {
            order.setOrderItems(new ArrayList<>());
        }

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : request.getItems()) {
            ProductVariant productVariant = productVariantRepository.findById(itemReq.getProductVariantId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));

            productVariant.setQuantity(productVariant.getQuantity() - itemReq.getQuantity());


            productVariantRepository.save(productVariant);

            BigDecimal itemPrice = productVariant.getPrice()
                    .multiply(BigDecimal.valueOf(itemReq.getQuantity()));

            OrderItem orderItem = OrderItem.builder()
                    .productVariant(productVariant)
                    .quantity(itemReq.getQuantity())
                    .price(productVariant.getPrice())
                    .order(order)
                    .build();

            order.getOrderItems().add(orderItem);
            totalAmount = totalAmount.add(itemPrice);
        }

        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toResponse(savedOrder);
    }

    @Override
    @Transactional
    public OrderResponse checkOutCart(CheckOutResquest request) {
        // Lấy thông tin user từ SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Tìm giỏ hàng đang ACTIVE
        Cart cart = cartRepository.findByUserAndStatus(user, CartStatus.ACTIVE)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        if (cart.getItems().isEmpty()) {
            throw new AppException(ErrorCode.CART_EMPTY);
        }

        // Tạo Order
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setShippingAddress(request.getShippingAddress());
        order.setShippingMethod(request.getShippingMethod());
        order.setNote(request.getNote());

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {
            ProductVariant productVariant = cartItem.getProductVariant();

            if (productVariant.getQuantity() < cartItem.getQuantity()) {
                throw new AppException(ErrorCode.PRODUCT_OUT_OF_STOCK);
            }

            productVariant.setQuantity(productVariant.getQuantity() - cartItem.getQuantity());

            productVariantRepository.save(productVariant);

            BigDecimal itemPrice = productVariant.getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            OrderItem orderItem = OrderItem.builder()
                    .productVariant(productVariant)
                    .quantity(cartItem.getQuantity())
                    .price(productVariant.getPrice()) // đơn giá
                    .order(order)
                    .build();

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(itemPrice);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        // Lưu order và orderItems
        orderRepository.save(order);

        // Xóa tất cả CartItem trong giỏ (reset giỏ hàng)
        cartService.clearCart(user.getId());

        return orderMapper.toResponse(order);
    }


    @Override
    public List<OrderResponse> getAllOrder() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    public OrderResponse getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return orderMapper.toResponse(order);
    }

    @Override
    public List<OrderResponse> getMyOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            System.out.println("Bạn không có quyền truy cập vào đây");
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    public OrderResponse updateStatus(Long id,OrderStatus newStatus) {
        //Tìm đơn hàng theo id
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new AppException(ErrorCode.ORDER_ALREADY_CANCELLED);
        }

        if (order.getStatus() == OrderStatus.SHIPPED && newStatus != OrderStatus.CANCELLED) {
            throw new AppException(ErrorCode.ORDER_ALREADY_COMPLETED);
        }

        order.setStatus(newStatus);
        orderRepository.save(order);
        return orderMapper.toResponse(order);
    }

}
