package com.nguyenvanphuong.apple_devices.controller;

import com.nguyenvanphuong.apple_devices.dtos.request.ProductRequest;
import com.nguyenvanphuong.apple_devices.dtos.request.ProductVariantRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ApiResponse;
import com.nguyenvanphuong.apple_devices.dtos.response.ProductResponse;
import com.nguyenvanphuong.apple_devices.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;

    //Tạo một product mới
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<ProductResponse> createProduct(
            @RequestBody ProductRequest request
    ) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(request))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<ProductResponse> updateProduct(@RequestBody ProductRequest request, @PathVariable Long id){
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(request, id))
                .build();
    }

    @GetMapping()
    //Endpoint public
    public ApiResponse<List<ProductResponse>> getAll(){
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    //Endpoint public
    public ApiResponse<ProductResponse> getById(@PathVariable Long id){
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getProductById(id))
                .build();
    }

    //xóa sản phẩm theo id và không xóa hẳn sản phẩm
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public  ApiResponse<?> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ApiResponse.builder()
                .result("Xóa thành công")
                .build();
    }

    //Tìm kiếm sản phẩm theo tên
    //Endpoint public
    @GetMapping("/search")
    public ApiResponse<List<ProductResponse>> searchByName(@RequestParam String keyword){
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.searcByName(keyword))
                .build();
    }
}
