package com.maximosan.onlineshop.controller;

import com.maximosan.onlineshop.common.ApiResponse;
import com.maximosan.onlineshop.model.Category;
import com.maximosan.onlineshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
        return new ResponseEntity<>(new ApiResponse(true, "created a new category"), HttpStatus.CREATED);
    }

    @GetMapping()
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @PostMapping("/update/{category_id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("category_id") int id, @RequestBody Category category) {
        categoryService.updateCategory(id, category);
        return new ResponseEntity<>(new ApiResponse(true, "updated category with id: "+ id), HttpStatus.OK);
    }
}
