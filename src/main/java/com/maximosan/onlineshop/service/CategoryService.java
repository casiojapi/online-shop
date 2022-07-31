package com.maximosan.onlineshop.service;

import com.maximosan.onlineshop.exception.CategoryDoesNotExistException;
import com.maximosan.onlineshop.model.Category;
import com.maximosan.onlineshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public void updateCategory(int id, Category newCategory) {
        Category category = findCategoryById(id);
        category.setLabel(newCategory.getLabel());
        categoryRepository.save(category);
    }

    public Category findCategoryById(Integer categoryId) {
        Optional<Category> wrappedCategory = categoryRepository.findById(categoryId);
        if (!wrappedCategory.isPresent()) {
            throw new CategoryDoesNotExistException("category with id: " + categoryId + " does not exist.");
        }
        return wrappedCategory.get();
    }

}
