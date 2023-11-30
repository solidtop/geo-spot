package com.example.geospot.category;

import com.example.geospot.exception.ApiRequestException;
import com.example.geospot.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public void addNewCategory(@Validated Category category) {
        categoryRepository.findByName(category.getName()).orElseThrow(() ->
                new ApiRequestException("Category already exists"));

        categoryRepository.save(category);
    }

    public Category getCategoryById(long id) {
        return categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
    }
}
