package com.nguyenvanphuong.apple_devices.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nguyenvanphuong.apple_devices.dtos.request.ProductVariantRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ProductVariantResponse;
import com.nguyenvanphuong.apple_devices.entity.Product;
import com.nguyenvanphuong.apple_devices.entity.ProductVariant;
import com.nguyenvanphuong.apple_devices.entity.ProductVariantStatus;
import com.nguyenvanphuong.apple_devices.exception.AppException;
import com.nguyenvanphuong.apple_devices.exception.ErrorCode;
import com.nguyenvanphuong.apple_devices.mapper.ProductVariantMapper;
import com.nguyenvanphuong.apple_devices.repository.ProductRepository;
import com.nguyenvanphuong.apple_devices.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService{
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductVariantMapper productVariantMapper;


    private final Cloudinary cloudinary;


    @Override
    public ProductVariantResponse createVariant(ProductVariantRequest request) throws IOException {
        // Tìm sản phẩm cha
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // Check unique SKU
        if (productVariantRepository.existsBySku(request.getSku())) {
            throw new AppException(ErrorCode.SKU_EXISTED);
        }

        if (request.getQuantity() == null || request.getQuantity() < 0) {
            throw new AppException(ErrorCode.INVALID_QUANTITY);
        }

        ProductVariant variant = productVariantMapper.toEntity(request);
        variant.setProduct(product);

        // Xử lý lưu ảnh lên Cloudinary
        MultipartFile file = request.getImageUrl();
        if (file != null && !file.isEmpty()) {
            try {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                        "public_id", UUID.randomUUID().toString(), // Tên file unique
                        "folder", "apple-devices" // Lưu vào folder trên Cloudinary (optional)
                ));
                String imageUrl = (String) uploadResult.get("secure_url"); // Lấy URL
                variant.setImageUrl(imageUrl); // Lưu URL vào DB
            } catch (IOException e) {
                throw new AppException(ErrorCode.UPLOAD_FAILED);
            }
        }

        // Lưu DB
        ProductVariant saved = productVariantRepository.save(variant);

        log.info("Đang ở service chưa return " + request);
        return productVariantMapper.toResponse(saved);
    }


    @Override
    public ProductVariantResponse updateVariant(ProductVariantRequest request) {
        return  null;
    }

    @Override
    public List<ProductVariantResponse> getVariantsByProductId(Long productId) {
        System.out.println("Phương thức lấy danh sách biến thể của sản phẩm theo sản phầm");
        return productVariantRepository.findByProductId(productId)
                .stream().map(productVariantMapper::toResponse)
                .toList();
    }

    @Override
    public ProductVariantResponse getVariantByProductAndColor(Long productId, String color, String memory) {

        System.out.println("Phương thức lấy biến thể sản phẩm theo ");
        ProductVariant variant = productVariantRepository.findByProductIdAndColorAndMemory(productId, color, memory)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        return productVariantMapper.toResponse(variant);
    }

    // bao gồm cả các variants
    @Override
    public List<ProductVariantResponse> getAll() {

        return productVariantRepository.findAll()
                .stream().map(productVariantMapper::toResponse)
                .toList();
    }

}
