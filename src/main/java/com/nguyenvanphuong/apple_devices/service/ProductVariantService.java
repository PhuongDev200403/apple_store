package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.ProductVariantRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ProductVariantResponse;
import com.nguyenvanphuong.apple_devices.entity.ProductVariant;

import java.io.IOException;

public interface ProductVariantService {
    ProductVariantResponse createVariant(ProductVariantRequest request) throws IOException;
}
