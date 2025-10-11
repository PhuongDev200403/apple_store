package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.AddToWishlistRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.WishlistResponse;
import com.nguyenvanphuong.apple_devices.entity.ProductVariant;
import com.nguyenvanphuong.apple_devices.entity.User;
import com.nguyenvanphuong.apple_devices.entity.Wishlist;
import com.nguyenvanphuong.apple_devices.entity.WishlistItem;
import com.nguyenvanphuong.apple_devices.exception.AppException;
import com.nguyenvanphuong.apple_devices.exception.ErrorCode;
import com.nguyenvanphuong.apple_devices.mapper.WishlistMapper;
import com.nguyenvanphuong.apple_devices.repository.ProductVariantRepository;
import com.nguyenvanphuong.apple_devices.repository.UserRepository;
import com.nguyenvanphuong.apple_devices.repository.WishlistItemRepository;
import com.nguyenvanphuong.apple_devices.repository.WishlistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class WishlistServiceImpl implements WishlistService{
    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WishlistItemRepository wishlistItemRepository;

    @Autowired
    ProductVariantRepository variantRepository;

    @Autowired
    WishlistMapper wishlistMapper;


    @Override
    public WishlistResponse addToWishlist(AddToWishlistRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Lấy wishlist hoặc tạo mới
        Wishlist wishlist = wishlistRepository.findByUserId(user.getId())
                .orElseGet(() -> wishlistRepository.save(
                        Wishlist.builder()
                                .user(user)
                                .items(new ArrayList<>())
                                .build()
                ));

        // Lấy variant đầy đủ (bao gồm cả product)
        ProductVariant variant = variantRepository.findByIdWithProduct(request.getProductVariantId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));

        log.info("Mã sản phẩm của variant là " + variant.getProduct().getId());


        // Kiểm tra đã tồn tại trong wishlist chưa
        boolean exists = wishlistItemRepository.existsByWishlistAndProductVariant(wishlist, variant);
        if (exists) {
            throw new AppException(ErrorCode.ITEM_ALREADY_EXISTS);
        }

        // Tạo mới WishlistItem
        WishlistItem wishlistItem = WishlistItem.builder()
                .wishlist(wishlist)
                .productVariant(variant)
                .build();

        wishlistItemRepository.save(wishlistItem);

        wishlist.getItems().add(wishlistItem);

        wishlistRepository.save(wishlist);

        return wishlistMapper.toResponse(wishlist);
    }


    @Override
    public WishlistResponse getMyWishlist() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            log.info("Bạn không có quyền truy cập ");
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Wishlist wishlist = wishlistRepository.findByUserId(user.getId()).orElseGet(() -> {
            Wishlist newWishlist = Wishlist.builder()
                    .items(new ArrayList<>())
                    .user(user)
                    .createdAt(LocalDateTime.now())
                    .build();
            log.info("Danh sách yêu thích được tạo");

            return wishlistRepository.save(newWishlist);
        });

        return wishlistMapper.toResponse(wishlist);
    }

    @Override
    public List<WishlistResponse> getAllWishlist() {
        List<Wishlist> wishlists = wishlistRepository.findAll();
        return wishlists.stream()
                .map(wishlistMapper::toResponse)
                .toList();
    }

    @Override
    public WishlistResponse getByUserId(Long userId) {

        //log.info("Danh sách yêu thích của người dùng "+ wishlistRepository.findByUserId(userId));

        return wishlistMapper.toResponse(wishlistRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    @Override
    public WishlistResponse deleteWishlistItemFromWishlist(Long productVariantId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Lấy wishlist của user
        Wishlist wishlist = wishlistRepository.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(ErrorCode.WISHLIST_NOT_FOUND));

        // Tìm productVariant
        ProductVariant variant = variantRepository.findById(productVariantId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));

        // Tìm wishlist item tương ứng
        WishlistItem wishlistItem = wishlistItemRepository.findByWishlistAndProductVariant(wishlist, variant)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));


        // Xóa item
        wishlist.getItems().remove(wishlistItem);
        wishlistItemRepository.delete(wishlistItem);

        // Cập nhật wishlist
        wishlistRepository.save(wishlist);

        return wishlistMapper.toResponse(wishlist);
    }

    @Override
    public WishlistResponse clearWishlist() {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        //Tìm danh sách yêu thích theo userId
        Wishlist wishlist = wishlistRepository.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(ErrorCode.WISHLIST_NOT_FOUND));

        if(wishlist.getItems().isEmpty()){
            throw new AppException(ErrorCode.INVALID_QUANTITY);
        }

        //Tìm item bằng trong wishlist sau đó xóa hết đi
        wishlistItemRepository.deleteByWishlistId(wishlist.getId());

        wishlist.getItems().clear();
        wishlist.setItems(new ArrayList<>());

        log.info("Danh sách yêu thích "+ wishlist.getItems());

        wishlistRepository.save(wishlist);

        return wishlistMapper.toResponse(wishlist);
    }
}
