package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.CategoryRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.CategoryResponse;
import com.nguyenvanphuong.apple_devices.entity.Category;
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
        Optional<Category> existingCategory = categoryRepository.findByName(request.getName());

        if (existingCategory.isPresent()) {
            throw new RuntimeException("Danh mục đã tồn tại");
        }

        // Tạo category mới nếu chưa tồn tại
        Category newCate = categoryMapper.toCategory(request);
        categoryRepository.save(newCate);
        return categoryMapper.toCategoryResponse(newCate);
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        return categoryRepository.findAll()
                .stream().map(categoryMapper::toCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if(!category.isPresent()){
            throw new RuntimeException("Category không tồn tại");
        }

        return categoryMapper.toCategoryResponse(category.get());
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest request, Long id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category không tồn tại"));

        // Kiểm tra trùng tên (nếu tên mới khác tên cũ)
        if (!existingCategory.getName().equals(request.getName())) {
            Optional<Category> duplicateCategory = categoryRepository.findByName(request.getName());
            if (duplicateCategory.isPresent()) {
                throw new RuntimeException("Tên danh mục đã tồn tại");
            }
        }

        // Cập nhật thông tin
        existingCategory.setName(request.getName());
        existingCategory.setDescription(request.getDescription()); // nếu có field này
        // Cập nhật các field khác nếu có...

        // Lưu vào database
        Category updatedCategory = categoryRepository.save(existingCategory);

        // Convert và return
        return categoryMapper.toCategoryResponse(updatedCategory);
    }

    @Override
    public String deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category không tồn tại"));

        categoryRepository.delete(category);
        return "Xóa thành công";
    }

    @Override
    public Boolean existed(String name) {
        return categoryRepository.existsByName(name);
    }

}
