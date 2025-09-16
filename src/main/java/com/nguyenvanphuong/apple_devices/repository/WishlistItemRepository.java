package com.nguyenvanphuong.apple_devices.repository;

import com.nguyenvanphuong.apple_devices.entity.ProductVariant;
import com.nguyenvanphuong.apple_devices.entity.Wishlist;
import com.nguyenvanphuong.apple_devices.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

    Boolean existsByWishlistAndProductVariant(Wishlist wishlist, ProductVariant variant);
    Optional<WishlistItem> findByWishlistAndProductVariant(Wishlist wishlist, ProductVariant variant);
    List<WishlistItem> findByWishlist(Wishlist wishlist);
    @Transactional
    @Modifying
    @Query("DELETE FROM WishlistItem wi WHERE wi.wishlist.id = :wishlistId")
    void deleteByWishlistId(@Param("wishlistId") Long wishlistId);
}
