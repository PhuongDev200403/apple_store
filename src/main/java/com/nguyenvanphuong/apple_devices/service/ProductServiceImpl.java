package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.ProductRequest;
import com.nguyenvanphuong.apple_devices.dtos.request.ProductVariantRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ProductResponse;
import com.nguyenvanphuong.apple_devices.entity.CategoryChild;
import com.nguyenvanphuong.apple_devices.entity.Product;
import com.nguyenvanphuong.apple_devices.entity.ProductVariant;
import com.nguyenvanphuong.apple_devices.mapper.ProductMapper;
import com.nguyenvanphuong.apple_devices.mapper.ProductVariantMapper;
import com.nguyenvanphuong.apple_devices.repository.CategoryChildRepository;
import com.nguyenvanphuong.apple_devices.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryChildRepository categoryChildRepository;
    private final ProductMapper productMapper;
    private final ProductVariantMapper productVariantMapper;
    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Product product = productMapper.toEntity(request);

        CategoryChild categoryChild = categoryChildRepository.findById(request.getCategoryChildId())
                .orElseThrow(() -> new RuntimeException("Danh mục con không tồn tại"));
        product.setCategoryChild(categoryChild);

//        List<ProductVariant> variantEntities = new ArrayList<>();
//        for (ProductVariantRequest variantReq : variants) {
//            ProductVariant variant = productVariantMapper.toEntity(variantReq);
//
//            // Lưu ảnh vào folder backend
//            if (variantReq.getImageUrl() != null && !variantReq.getImageUrl().isEmpty()) {
//                String filePath = saveImage(variantReq.getImageUrl());
//                variant.setImageUrl(filePath);
//            }
//
//            variant.setProduct(product);
//            variantEntities.add(variant);
//        }
//        product.setProductVariants(variantEntities);

        productRepository.save(product);
        return productMapper.toResponse(product);
    }

//    private String saveImage(MultipartFile file) {
//        try {
//            String folderPath = "uploads/images/";
//            File folder = new File(folderPath);
//            if (!folder.exists()) {
//                folder.mkdirs();
//            }
//            String filePath = folderPath + System.currentTimeMillis() + "_" + file.getOriginalFilename();
//            file.transferTo(new File(filePath));
//            return filePath;
//        } catch (IOException e) {
//            throw new RuntimeException("Lỗi khi lưu ảnh", e);
//        }
//    }

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream().map(productMapper::toResponse).toList();
    }

    @Override
    public ProductResponse getProductById(Long id) {

        return null;
    }

    @Override
    public boolean existed(String name) {
        return productRepository.existsByName(name);
    }
}
