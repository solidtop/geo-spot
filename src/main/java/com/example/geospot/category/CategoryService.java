package com.example.geospot.category;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void addNewCategory(@Validated Category category) {
        Optional<Category> categoryOptional = categoryRepository.findCategoryByName(category.getName());
        if (categoryOptional.isPresent()) {
            throw new IllegalStateException("Category already exists");
        }

        categoryRepository.save(category);
    }

    public Optional<Category> getCategoryById(int id) {
        return categoryRepository.findById(id);
    }
}
