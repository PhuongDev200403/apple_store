package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.ProductRequest;
import com.nguyenvanphuong.apple_devices.dtos.request.ProductVariantRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ProductResponse;
import com.nguyenvanphuong.apple_devices.entity.CategoryChild;
import com.nguyenvanphuong.apple_devices.entity.Product;
import com.nguyenvanphuong.apple_devices.entity.ProductStatus;
import com.nguyenvanphuong.apple_devices.entity.ProductVariant;
import com.nguyenvanphuong.apple_devices.exception.AppException;
import com.nguyenvanphuong.apple_devices.exception.ErrorCode;
import com.nguyenvanphuong.apple_devices.mapper.ProductMapper;
import com.nguyenvanphuong.apple_devices.mapper.ProductVariantMapper;
import com.nguyenvanphuong.apple_devices.repository.CategoryChildRepository;
import com.nguyenvanphuong.apple_devices.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
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

        if(productRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }

        Product product = productMapper.toEntity(request);

        CategoryChild categoryChild = categoryChildRepository.findById(request.getCategoryChildId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_CHILD_NOT_FOUND));
        product.setCategoryChild(categoryChild);

        product.setStatus(ProductStatus.ACTIVE);

        productRepository.save(product);
        return productMapper.toResponse(product);
    }


    //chỉ lấy danh sách sản phẩm mà có status là true
    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findByStatusTrue()
                .stream().map(productMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse updateProduct(ProductRequest request, Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        productMapper.updateFromRequest(request, product);
        product.setUpdateAt(LocalDateTime.now());

        Product updatedProduct = productRepository.save(product);

        return productMapper.toResponse(updatedProduct);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        return productMapper.toResponse(product);
    }


    //khi xóa sản phẩm thì chỉ cập nhật trạng thais của sản phẩm là false thôi
    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setStatus(ProductStatus.INACTIVE);

        productRepository.save(product);
    }

    @Override
    public List<ProductResponse> searcByName(String keyword) {
        return productRepository.searchByNameNative(keyword)
                .stream().map(productMapper::toResponse)
                .toList();
    }
}
