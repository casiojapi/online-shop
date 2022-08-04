package com.maximosan.onlineshop.service;

import com.maximosan.onlineshop.dto.ProductDTO;
import com.maximosan.onlineshop.exception.OrderDoesNotExistException;
import com.maximosan.onlineshop.exception.ProductDoesNotExistException;
import com.maximosan.onlineshop.model.Category;
import com.maximosan.onlineshop.model.Product;
import com.maximosan.onlineshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product productMock;
    private Category categoryMock;

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

    }

    @Test
    void createProductTest() {
        assertNotNull(productService.getProducts());
        assert(productService.getProducts().isEmpty());
        when(productRepository.findAll()).thenReturn(Arrays.asList(productMock));

        ProductDTO dto = new ProductDTO();
        dto.setCategoryId(categoryMock.getId());
        dto.setId(productMock.getId());
        dto.setWeight(productMock.getWeight());
        dto.setPhysical(productMock.isPhysical());
        dto.setDownloadUrl(productMock.getDownloadUrl());
        dto.setPrice(productMock.getPrice());
        dto.setLabel(productMock.getLabel());

        productService.createProduct(dto, categoryMock);

        assertNotNull(productService.getProducts());
        assertEquals(productService.getProducts().get(0).getLabel(), dto.getLabel());
        assertEquals(productService.getProducts().get(0).getPrice(), dto.getPrice());

    }

    @Test
    void getProductsWithOneProductCreatedTest() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(productMock));
        assertNotNull(productService.getProducts());
        assertEquals(productService.getProducts().get(0).getLabel(), productMock.getLabel());
        assertEquals(productService.getProducts().get(0).getPrice(), productMock.getPrice());
    }

    @Test
    void updateProductTest() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(productMock));
        assertNotNull(productService.getProducts());
        assertEquals(productService.getProducts().get(0).getLabel(), productMock.getLabel());
        assertEquals(productService.getProducts().get(0).getPrice(), productMock.getPrice());


        ProductDTO updatedDto = new ProductDTO();
        updatedDto.setCategoryId(categoryMock.getId());
        updatedDto.setWeight(productMock.getWeight());
        updatedDto.setPhysical(productMock.isPhysical());
        updatedDto.setDownloadUrl("wikipedia.org");
        updatedDto.setPrice(200l);
        updatedDto.setLabel("blade runner trailer [EXTENDED]");

        when(productRepository.findById(1)).thenReturn(Optional.of(productMock));
        productService.updateProduct(updatedDto, productMock.getId(), categoryMock);


        assertEquals(productService.getProducts().get(0).getLabel(), updatedDto.getLabel());
        assertEquals(productService.getProducts().get(0).getPrice(), updatedDto.getPrice());

    }

    @Test
    void findProductByIdTest() {
        when(productRepository.findById(1)).thenReturn(Optional.of(productMock));

        assertEquals(productService.findProductById(1).getCategory(), productMock.getCategory());
        assertEquals(productService.findProductById(1).isPhysical(), productMock.isPhysical());
    }

    @Test
    void findProductThatDoesNotExistExceptionTest() {
        when(productRepository.findById(2)).thenThrow(ProductDoesNotExistException.class);
        assertThrows(ProductDoesNotExistException.class,
                ()->{productService.findProductById(2);});
    }
}