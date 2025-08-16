package com.nguyenvanphuong.apple_devices.controller;

import com.nguyenvanphuong.apple_devices.dtos.request.ProductVariantRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ProductVariantResponse;
import com.nguyenvanphuong.apple_devices.entity.ProductVariant;
import com.nguyenvanphuong.apple_devices.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/variants")
public class ProductVariantController {
    @Autowired
    ProductVariantService productVariantService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductVariantResponse> createVariant(
            @ModelAttribute ProductVariantRequest request) throws IOException {
        return ResponseEntity.ok(productVariantService.createVariant(request));
    }
}
