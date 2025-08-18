package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.CategoryRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.CategoryResponse;
import com.nguyenvanphuong.apple_devices.entity.Category;
import com.nguyenvanphuong.apple_devices.exception.AppException;
import com.nguyenvanphuong.apple_devices.exception.ErrorCode;
import com.nguyenvanphuong.apple_devices.mapper.CategoryMapper;
import com.nguyenvanphuong.apple_devices.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public CategoryResponse creatNewCategory(CategoryRequest request) {
        // Kiểm tra tồn tại
        if (categoryRepository.findByName(request.getName()).isPresent()) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }

        // Tạo mới và lưu
        Category newCategory = categoryMapper.toCategory(request);
        categoryRepository.save(newCategory);

        // Trả về response
        return categoryMapper.toCategoryResponse(newCategory);
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        return categoryRepository.findAll()
                .stream().map(categoryMapper::toCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest request, Long id) {
        // Tìm category theo id
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        // Kiểm tra trùng tên (nếu tên mới khác tên cũ)
        if (!existingCategory.getName().equalsIgnoreCase(request.getName())) {
            categoryRepository.findByName(request.getName())
                    .ifPresent(duplicate -> {
                        throw new AppException(ErrorCode.CATEGORY_EXISTED);
                    });
        }

        // Cập nhật thông tin
        existingCategory.setName(request.getName());
        existingCategory.setDescription(request.getDescription());

        // Lưu vào database
        Category updatedCategory = categoryRepository.save(existingCategory);

        return categoryMapper.toCategoryResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        categoryRepository.delete(category);
    }

    @Override
    public Boolean existed(String name) {
        return categoryRepository.existsByName(name);
    }

}
