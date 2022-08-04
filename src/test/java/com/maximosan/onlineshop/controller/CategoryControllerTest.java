package com.maximosan.onlineshop.controller;

import com.maximosan.onlineshop.common.ApiResponse;
import com.maximosan.onlineshop.model.Category;
import com.maximosan.onlineshop.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;
    @Mock
    private CategoryService categoryService;
    private Category categoryMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryMock = new Category();
        categoryMock.setLabel("video");
        categoryMock.setId(1);
    }

    @Test
    void createCategoryTest() {
       ResponseEntity<ApiResponse> res = categoryController.createCategory(categoryMock);
       assertEquals(res.getStatusCode(), HttpStatus.CREATED);
       assert(res.getBody().isSuccess());
    }

    @Test
    void getCategoriesTest() {
        when(categoryService.getCategories()).thenReturn(Arrays.asList(categoryMock));
        ResponseEntity<List<Category>> res = categoryController.getCategories();
        assertEquals(res.getStatusCode(), HttpStatus.OK);
        assertEquals(res.getBody().get(0), categoryMock);
    }

    @Test
    void updateCategoryTest() {
        ResponseEntity<ApiResponse> res = categoryController.updateCategory(1, categoryMock);
        assertEquals(res.getStatusCode(), HttpStatus.OK);
        assert(res.getBody().isSuccess());
    }
}