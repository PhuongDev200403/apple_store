package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.ProductRequest;
import com.nguyenvanphuong.apple_devices.dtos.request.ProductVariantRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ProductResponse;
import com.nguyenvanphuong.apple_devices.entity.Product;

import java.util.List;

public interface ProductService {
    //Phương thức tạo sản phẩm
    ProductResponse createProduct(ProductRequest request);

    //Phương thức lấy tất cả sản phẩm theo id
    List<ProductResponse> getAll();

    //Phương thức cập nhật sản phẩm
    ProductResponse updateProduct(ProductRequest request, Long id);

    //Phương thức lấy sản phẩm theo id
    ProductResponse getProductById(Long id);

    //Phương thức xóa sản phẩm theo id
    void deleteProduct(Long id);

    //Phương thức lấy danh sách sản phẩm theo danh mục

    //Phương thức tìm kiếm sản phẩm theo tên
    List<ProductResponse> searcByName(String keyword);
}
