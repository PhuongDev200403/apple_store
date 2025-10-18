package com.nguyenvanphuong.apple_devices.controller;

import com.nguyenvanphuong.apple_devices.dtos.request.ProductVariantRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ApiResponse;
import com.nguyenvanphuong.apple_devices.dtos.response.ProductVariantResponse;
import com.nguyenvanphuong.apple_devices.entity.ProductVariant;
import com.nguyenvanphuong.apple_devices.exception.AppException;
import com.nguyenvanphuong.apple_devices.exception.ErrorCode;
import com.nguyenvanphuong.apple_devices.repository.ProductVariantRepository;
import com.nguyenvanphuong.apple_devices.service.ProductVariantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/variants")
@Slf4j
public class ProductVariantController {
    @Autowired
    ProductVariantService productVariantService;

    @Autowired
    ProductVariantRepository productVariantRepository;

    //Tạo mới một biến thể của sản phẩm chỉ có admin mới được tạo
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ProductVariantResponse> createVariant(
            @ModelAttribute ProductVariantRequest request) throws IOException {
        log.info("Request: " + request);
        return ApiResponse.<ProductVariantResponse>builder()
                .result(productVariantService.createVariant(request))
                .build();
    }

    //Lấy sản phẩm theo màu
    @GetMapping("/by-color")
    public ApiResponse<Object> getVariantByProductAndColor(
            @RequestParam Long productId,
            @RequestParam String color,
            @RequestParam String memory
    ) {
        return ApiResponse.builder()
                .result(productVariantService.getVariantByProductAndColor(productId, color, memory))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<List<ProductVariantResponse>> getVariantByProductId(@PathVariable Long id){
        return ApiResponse.<List<ProductVariantResponse>>builder()
                .result(productVariantService.getVariantsByProductId(id))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<ProductVariantResponse>> getAll(){
        log.info("Bên controller");
        return ApiResponse.<List<ProductVariantResponse>>builder()
                .result(productVariantService.getAll())
                .build();
    }

}
