package com.nguyenvanphuong.apple_devices.dtos.response;

import com.nguyenvanphuong.apple_devices.entity.ProductVariant;
import com.nguyenvanphuong.apple_devices.entity.Wishlist;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WishlistItemResponse {
    Long id;
    ProductVariantResponse productVariant;
    LocalDateTime addedAt;
}
