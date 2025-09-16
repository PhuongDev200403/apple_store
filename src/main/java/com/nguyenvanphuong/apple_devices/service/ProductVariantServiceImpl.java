package com.nguyenvanphuong.apple_devices.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService{
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductVariantMapper productVariantMapper;

    private static final String UPLOAD_DIR = "uploads/images/";

    @Override
    public ProductVariantResponse createVariant(ProductVariantRequest request) throws IOException {
        // Tìm sản phẩm cha

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // Check unique SKU
        if (productVariantRepository.existsBySku(request.getSku())) {
            throw new AppException(ErrorCode.SKU_EXISTED);
        }

        if(request.getQuantity() == null || request.getQuantity() < 0){
            throw new AppException(ErrorCode.INVALID_QUANTITY);
        }

        ProductVariant variant = productVariantMapper.toEntity(request);
        variant.setProduct(product);

        // Xử lý lưu file ảnh
        MultipartFile file = request.getImageUrl();
        if (file != null && !file.isEmpty()) {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            variant.setImageUrl(fileName);
        }

        // Lưu DB
        ProductVariant saved = productVariantRepository.save(variant);
        return productVariantMapper.toResponse(saved);
    }

    //Xóa thì chỉ cập nhật status thành false thôi
    @Override
    public void deleteVariant(Long id) {
        ProductVariant productVariant = productVariantRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));

        productVariant.setStatus(ProductVariantStatus.INACTIVE);
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

    @Override
    public List<ProductVariantResponse> getAll() {
        return productVariantRepository.findAll().stream().map(productVariantMapper::toResponse).toList();
    }

}
