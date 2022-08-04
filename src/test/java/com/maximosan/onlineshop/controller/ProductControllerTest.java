package com.maximosan.onlineshop.controller;

import com.maximosan.onlineshop.common.ApiResponse;
import com.maximosan.onlineshop.dto.ProductDTO;
import com.maximosan.onlineshop.model.Category;
import com.maximosan.onlineshop.model.Product;
import com.maximosan.onlineshop.service.CategoryService;
import com.maximosan.onlineshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private CategoryService categoryService;
    @Mock
    private ProductService productService;
    private Category categoryMock;
    private Product productMock;
    private ProductDTO productDtoMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryMock = new Category();
        categoryMock.setLabel("video");
        categoryMock.setId(1);

        productMock = new Product();
        productMock.setCategory(categoryMock);
        productMock.setLabel("blade runner trailer");
        productMock.setPhysical(false);
        productMock.setPrice(100L);
        productMock.setDownloadUrl("google.com");
        productMock.setId(1);

        productDtoMock = new ProductDTO();
        productDtoMock.setLabel(productMock.getLabel());
        productDtoMock.setWeight(productMock.getWeight());
        productDtoMock.setId(productMock.getId());
        productDtoMock.setPhysical(productMock.isPhysical());
        productDtoMock.setPrice(productMock.getPrice());
        productDtoMock.setDownloadUrl(productMock.getDownloadUrl());
        productDtoMock.setCategoryId(productMock.getCategory().getId());


    }

    @Test
    void createProductTest() {
        when(categoryService.findCategoryById(1)).thenReturn(categoryMock);
        ResponseEntity<ApiResponse> res = productController.createProduct(productDtoMock);
        assertEquals(res.getStatusCode(), HttpStatus.CREATED);
        assert(res.getBody().isSuccess());
    }

    @Test
    void getProductsTest() {
        when(productService.getProducts()).thenReturn(Arrays.asList(productDtoMock));
        ResponseEntity<List<ProductDTO>> res = productController.getProducts();
        assertEquals(res.getStatusCode(), HttpStatus.OK);
        assertEquals(res.getBody().get(0), productDtoMock);
    }

    @Test
    void updateProductTest() {
        when(categoryService.findCategoryById(1)).thenReturn(categoryMock);
        ResponseEntity<ApiResponse> res = productController.updateProduct(1, productDtoMock);
        assertEquals(res.getStatusCode(), HttpStatus.OK);
        assert(res.getBody().isSuccess());
    }
}