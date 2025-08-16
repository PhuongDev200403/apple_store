package com.nguyenvanphuong.apple_devices.controller;

import com.nguyenvanphuong.apple_devices.dtos.request.ProductRequest;
import com.nguyenvanphuong.apple_devices.dtos.request.ProductVariantRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ProductResponse;
import com.nguyenvanphuong.apple_devices.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping()
    public ResponseEntity<?> createProduct(
            @RequestBody ProductRequest request
    ) {
        boolean exists = productService.existed(request.getName());
        if (exists){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Sản phẩm đã tồn tại");
        }

        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponse>> getAll(){
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }
}
