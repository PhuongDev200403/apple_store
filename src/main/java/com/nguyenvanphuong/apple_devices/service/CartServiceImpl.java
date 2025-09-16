package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.AddToCartRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.CartItemResponse;
import com.nguyenvanphuong.apple_devices.dtos.response.CartResponse;
import com.nguyenvanphuong.apple_devices.dtos.response.UserResponse;
import com.nguyenvanphuong.apple_devices.entity.*;
import com.nguyenvanphuong.apple_devices.exception.AppException;
import com.nguyenvanphuong.apple_devices.exception.ErrorCode;
import com.nguyenvanphuong.apple_devices.mapper.CartMapper;
import com.nguyenvanphuong.apple_devices.mapper.UserMapper;
import com.nguyenvanphuong.apple_devices.repository.CartItemRepository;
import com.nguyenvanphuong.apple_devices.repository.CartRepository;
import com.nguyenvanphuong.apple_devices.repository.ProductVariantRepository;
import com.nguyenvanphuong.apple_devices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductVariantRepository productVariantRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;

    @Override
    public CartResponse addToCart(AddToCartRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var email = authentication.getName(); // name chính là email

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        //kiểm tra giỏ hàng tồn tại chưa, nếu chưa thì tạo giỏ hàng mới
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .user(user)
                            .status(CartStatus.ACTIVE)
                            .build();
                    return cartRepository.save(newCart);
                });

        ProductVariant variant = productVariantRepository.findById(request.getProductVariantId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));

        if(variant.getQuantity() <= 0 || variant.getStatus() == ProductVariantStatus.OUT_OF_STOCK){
            throw new AppException(ErrorCode.FAILED_TO_ADD_TO_CART);
        }

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProductVariant().getId().equals(variant.getId()))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = CartItem.builder()
                            .cart(cart)
                            .productVariant(variant)
                            .unitPrice(variant.getPrice())
                            .productName(variant.getProduct().getName())
                            .productImage(variant.getImageUrl())
                            .productSku(variant.getSku())
                            .status(CartItemStatus.ACTIVE)
                            .quantity(0)
                            .build();
                    cart.getItems().add(newItem);
                    return newItem;
                });

        // Cập nhật số lượng
        cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        cartItem.setTotalPrice(cartItem.getUnitPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity())));

        // Update tổng số lượng giỏ hàng
        cart.setTotalQuantity(cart.getItems().stream()
                .mapToLong(CartItem::getQuantity)
                .sum());

        cartRepository.save(cart);

        return cartMapper.toResponse(cart);
    }

    @Override
    public CartResponse getCartByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Cart cart = cartRepository.findByUserAndStatus(user, CartStatus.ACTIVE)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .user(user)
                            .status(CartStatus.ACTIVE)
                            .build();
                    return cartRepository.save(newCart);
                });
        return cartMapper.toResponse(cart);
    }

    @Override
    public CartResponse clearCart(Long targetUserId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Lấy email (sub) từ token
        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Kiểm tra xem caller có ROLE_ADMIN không (dùng authorities từ Authentication)
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));

        User targetUser;
        if (isAdmin && targetUserId != null) {
            // Admin muốn clear cho user khác
            targetUser = userRepository.findById(targetUserId)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        } else {
            // User bình thường hoặc admin không truyền userId -> clear giỏ của chính họ
            targetUser = currentUser;
        }

        Cart cart = cartRepository.findByUserAndStatus(targetUser, CartStatus.ACTIVE)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        // Xóa item (chú ý nếu cart.getItems() = null thì khởi tạo)
        if (cart.getItems() != null) {
            cart.getItems().clear();
        }
        cart.setTotalQuantity(0L);
        cart.setUpdateAt(LocalDateTime.now());

        Cart saved = cartRepository.save(cart);
        return cartMapper.toResponse(saved);
    }

    @Override
    public CartResponse getMyCart() {
        return null;
    }
}
