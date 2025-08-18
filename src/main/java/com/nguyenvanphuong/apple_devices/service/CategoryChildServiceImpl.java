package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.CategoryChildRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.CategoryChildResponse;

import com.nguyenvanphuong.apple_devices.entity.Category;
import com.nguyenvanphuong.apple_devices.entity.CategoryChild;
import com.nguyenvanphuong.apple_devices.exception.AppException;
import com.nguyenvanphuong.apple_devices.exception.ErrorCode;
import com.nguyenvanphuong.apple_devices.mapper.CategorychildMapper;
import com.nguyenvanphuong.apple_devices.repository.CategoryChildRepository;
import com.nguyenvanphuong.apple_devices.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryChildServiceImpl implements CategoryChildService{
    @Autowired
    CategoryChildRepository categoryChildRepository;

    @Autowired
    CategorychildMapper categorychildMapper;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public CategoryChildResponse createNewCateChild(CategoryChildRequest request) {
        if (categoryChildRepository.findByName(request.getName()).isPresent()) {
            throw new AppException(ErrorCode.CATEGORY_CHILD_EXISTED);
        }

        // Kiểm tra category cha có tồn tại không
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        // Mapping từ request sang entity
        CategoryChild categoryChild = categorychildMapper.toCategoryChild(request);
        categoryChild.setCategory(category);

        // Lưu và trả response
        categoryChildRepository.save(categoryChild);
        return categorychildMapper.toCategoryChildResponse(categoryChild);
    }

    @Override
    public CategoryChildResponse updateCateChild(CategoryChildRequest request, Long id) {
        CategoryChild cate = categoryChildRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_CHILD_NOT_FOUND));

        // Nếu có CategoryId mới thì kiểm tra và set lại
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            cate.setCategory(category);
        }

        // Update từng field (nếu request có giá trị)
        if (request.getName() != null) {
            cate.setName(request.getName());
        }
        if (request.getDescription() != null) {
            cate.setDescription(request.getDescription());
        }
        // ... tiếp tục cho các field khác

        categoryChildRepository.save(cate);

        return categorychildMapper.toCategoryChildResponse(cate);
    }

    @Override
    public List<CategoryChildResponse> getAllCateChild() {
        return categoryChildRepository.findAll().stream()
                .map(categorychildMapper::toCategoryChildResponse)
                .toList();
    }

    @Override
    public void deleteCateChild(Long id) {
        CategoryChild categoryChild = categoryChildRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_CHILD_NOT_FOUND));

        categoryChildRepository.delete(categoryChild);
    }

    @Override
    public CategoryChildResponse getCateChildById(Long id) {
        CategoryChild categoryChild = categoryChildRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_CHILD_NOT_FOUND));

        return categorychildMapper.toCategoryChildResponse(categoryChild);
    }

    @Override
    public List<CategoryChildResponse> getCateChileByCategoryId(Long id) {
        // Kiểm tra category cha
        categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        // Lấy danh sách con
        return categoryChildRepository.findAllByCategoryId(id).stream()
                .map(categorychildMapper::toCategoryChildResponse)
                .toList();
    }
}
