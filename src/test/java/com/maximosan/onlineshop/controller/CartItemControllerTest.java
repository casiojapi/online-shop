package com.maximosan.onlineshop.controller;

import com.maximosan.onlineshop.common.ApiResponse;
import com.maximosan.onlineshop.dto.CartDTO;
import com.maximosan.onlineshop.dto.CartItemDTO;
import com.maximosan.onlineshop.dto.ProductDTO;
import com.maximosan.onlineshop.model.CartItem;
import com.maximosan.onlineshop.model.Category;
import com.maximosan.onlineshop.model.Product;
import com.maximosan.onlineshop.service.CartItemService;
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

class CartItemControllerTest {

    @InjectMocks
    private CartItemController cartItemController;

    @Mock
    private CartItemService cartItemService;

    private Category categoryMock;
    private Product productMock;
    private ProductDTO productDtoMock;
    private CartItemDTO cartItemMock;
    private CartDTO cartMock;

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

        cartItemMock = new CartItemDTO();
        cartItemMock.setId(1);
        cartItemMock.setProduct(productMock);

        cartMock = new CartDTO();
        cartMock.setCartItems(Arrays.asList(cartItemMock));
        cartMock.setTotalCost(100l);


    }

    @Test
    void addProductToCartTest() {
        ResponseEntity<ApiResponse> res = cartItemController.addProductToCart(1);

        assert(res.getBody().isSuccess());
        assertEquals(res.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getProductsFromCartTest() {
        when(cartItemService.getCartItems()).thenReturn(cartMock);
        ResponseEntity<CartDTO> res = cartItemController.getProductsFromCart();

        assertEquals(res.getBody().getCartItems(), cartMock.getCartItems());
        assertEquals(res.getBody().getTotalCost(), cartMock.getTotalCost());

        assertEquals(res.getStatusCode(), HttpStatus.OK);

    }

    @Test
    void cleanCartTest() {
        ResponseEntity<ApiResponse> res = cartItemController.cleanCart();
        assert(res.getBody().isSuccess());
        assertEquals(res.getStatusCode(), HttpStatus.OK);
    }
}