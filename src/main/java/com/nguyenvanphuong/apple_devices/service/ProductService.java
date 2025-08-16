package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.ProductRequest;
import com.nguyenvanphuong.apple_devices.dtos.request.ProductVariantRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.ProductResponse;

import java.util.List;

public interface ProductService {
    //Phương thức tạo sản phẩm
    ProductResponse createProduct(ProductRequest request);

    //Phương thức lấy tất cả sản phẩm theo id
    List<ProductResponse> getAll();

    //Phương thức lấy sản phẩm theo id
    ProductResponse getProductById(Long id);

    //Phương thức xóa sản phẩm theo id

    //Phương thức kiểm tra xem danh mục tồn tại chưa
    boolean existed(String name);
}
