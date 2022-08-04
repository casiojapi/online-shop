package com.maximosan.onlineshop.service;

import com.maximosan.onlineshop.exception.CategoryDoesNotExistException;
import com.maximosan.onlineshop.exception.OrderDoesNotExistException;
import com.maximosan.onlineshop.model.Category;
import com.maximosan.onlineshop.model.Product;
import com.maximosan.onlineshop.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
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
        assertNotNull(categoryService.getCategories());
        assert(categoryService.getCategories().isEmpty());

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(categoryMock));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(categoryMock));

        categoryService.createCategory(categoryMock);

        assertNotNull(categoryService.getCategories().get(0));
        assertEquals(categoryService.findCategoryById(1).getId(), categoryMock.getId());
        assertEquals(categoryService.findCategoryById(1).getLabel(), categoryMock.getLabel());

    }

    @Test
    void getCategoriesWithOneCategoryCreatedTest() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(categoryMock));
        assertNotNull(categoryService.getCategories());
        assertEquals(categoryService.getCategories().get(0).getLabel(), categoryMock.getLabel());
        assertEquals(categoryService.getCategories().get(0).getId(), categoryMock.getId());
    }

    @Test
    void updateOneCategoryTest() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(categoryMock));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(categoryMock));

        Category newCategory = new Category();
        newCategory.setId(1);
        newCategory.setLabel("photos");
        categoryService.updateCategory(1, newCategory);

        assertEquals(categoryService.getCategories().get(0).getLabel(), newCategory.getLabel());
        assertEquals(categoryService.getCategories().get(0).getId(), newCategory.getId());
    }

    @Test
    void findCategoryByIdTest() {
        when(categoryRepository.findById(1)).thenReturn(Optional.of(categoryMock));
        assertEquals(categoryService.findCategoryById(1).getLabel(), categoryMock.getLabel());
        assertEquals(categoryService.findCategoryById(1).getId(), categoryMock.getId());
    }

    @Test
    void findCategoryThatDoesNotExistExceptionTest() {
        when(categoryRepository.findById(2)).thenThrow(CategoryDoesNotExistException.class);
        assertThrows(CategoryDoesNotExistException.class,
                ()->{categoryService.findCategoryById(2);});
    }
}