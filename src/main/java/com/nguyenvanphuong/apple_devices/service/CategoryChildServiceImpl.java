package com.nguyenvanphuong.apple_devices.service;

import com.nguyenvanphuong.apple_devices.dtos.request.CategoryChildRequest;
import com.nguyenvanphuong.apple_devices.dtos.response.CategoryChildResponse;

import com.nguyenvanphuong.apple_devices.entity.Category;
import com.nguyenvanphuong.apple_devices.entity.CategoryChild;
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
        Optional<CategoryChild> cate = categoryChildRepository.findByName(request.getName());

        if(cate.isPresent()){
            throw new RuntimeException("Danh mục này đã tồn tại");
        }

        CategoryChild categoryChild = categorychildMapper.toCategoryChild(request);

        Category category = categoryRepository.findById(request.getCategoryId()).get();

        if(category == null){
            throw new RuntimeException("Danh mục cha không tồn tại");
        }

        categoryChild.setCategory(category);

        categoryChildRepository.save(categoryChild);

        return categorychildMapper.toCategoryChildResponse(categoryChild);
    }

    @Override
    public CategoryChildResponse updateCateChild(CategoryChildRequest request, Long id) {
        CategoryChild cate = categoryChildRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục con"));

        // Nếu có CategoryId mới thì kiểm tra và set lại
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Danh mục cha không tồn tại"));
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
    public String deleteCateChild(Long id) {
        Optional<CategoryChild> categoryChild = categoryChildRepository.findById(id);

        if(!categoryChild.isPresent()){
            throw new RuntimeException("Danh mục con không tồn tại");
        }
        categoryChildRepository.delete(categoryChild.get());
        return "Xóa thành công";
    }

    @Override
    public CategoryChildResponse getCateChildById(Long id) {
        Optional<CategoryChild> categoryChild = categoryChildRepository.findById(id);

        if(!categoryChild.isPresent()){
            throw new RuntimeException("Danh mục con không tồn tại");
        }
        return categorychildMapper.toCategoryChildResponse(categoryChild.get());
    }

    @Override
    public List<CategoryChildResponse> getCateChileByCategoryId(Long id) {
        List<CategoryChild> categoryChildList = categoryChildRepository.findAllByCategoryId(id);
        if (categoryChildList == null){
            throw new RuntimeException("Danh mục cha không tồn tại");
        }
        return  categoryChildList.stream().map(categorychildMapper::toCategoryChildResponse)
                .toList();
    }
}
