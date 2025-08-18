package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.ProductVariantRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ProductVariantResponse;
import com.nguyenvanphuong.apple_devices.entity.ProductVariant;

import java.io.IOException;
import java.util.List;

public interface ProductVariantService {
    //Phương thức tạo mới một product variant
    ProductVariantResponse createVariant(ProductVariantRequest request) throws IOException;

    //Xóa một product variant
    void deleteVariant(Long id);

    //Cập nhật thông tin của variant
    ProductVariantResponse updateVariant(ProductVariantRequest request);

    //Lấy danh sách variant theo id của sản phẩm
    List<ProductVariantResponse> getAllByProductId(Long productId);
}
