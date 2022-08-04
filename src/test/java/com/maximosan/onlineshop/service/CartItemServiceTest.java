package com.maximosan.onlineshop.service;

import com.maximosan.onlineshop.model.CartItem;
import com.maximosan.onlineshop.model.Category;
import com.maximosan.onlineshop.model.Product;
import com.maximosan.onlineshop.repository.CartItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CartItemServiceTest {

    @InjectMocks
    CartItemService cartItemService;

    @Mock
    CartItemRepository cartItemRepository;

    @Mock
    ProductService productService;

    private Category categoryMock;
    private Product productMock;
    private CartItem cartItemMock;


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

        cartItemMock = new CartItem();
        cartItemMock.setId(1);
        cartItemMock.setProduct(productMock);
        cartItemMock.setQuantity(1);
        cartItemMock.setCreatedDate(LocalDate.now());
    }

    @Test
    void addToCartOneItemTest() {
        assertNotNull(cartItemRepository.findAll());

        when(productService.findProductById(1)).thenReturn(productMock);

        cartItemService.addToCart(1);

        when(cartItemRepository.findAll()).thenReturn(Arrays.asList(cartItemMock));

        assertFalse(cartItemService.getCartItems().getCartItems().isEmpty());

        assertEquals(cartItemService.getCartItems().getCartItems().get(0).getProduct(), cartItemMock.getProduct());
        assertEquals(cartItemService.getCartItems().getCartItems().get(0).getId(), cartItemMock.getId());
        assertEquals(cartItemService.getCartItems().getCartItems().get(0).getQuantity(), cartItemMock.getQuantity());
    }

    @Test
    void getCartItemsIsEmptyTest() {
        assertNotNull(cartItemRepository.findAll());

        assert(cartItemService.getCartItems().getCartItems().isEmpty());
    }

    @Test
    void getCartItemsWithOneItemTest() {
        assertNotNull(cartItemRepository.findAll());

        when(productService.findProductById(1)).thenReturn(productMock);
        when(cartItemRepository.findAll()).thenReturn(Arrays.asList(cartItemMock));

        assertFalse(cartItemService.getCartItems().getCartItems().isEmpty());

        assertEquals(cartItemService.getCartItems().getCartItems().get(0).getProduct(), cartItemMock.getProduct());
        assertEquals(cartItemService.getCartItems().getCartItems().get(0).getId(), cartItemMock.getId());
        assertEquals(cartItemService.getCartItems().getCartItems().get(0).getQuantity(), cartItemMock.getQuantity());
    }

    @Test
    void cleanCartWithOneItemTest() {
        when(productService.findProductById(1)).thenReturn(productMock);
        cartItemService.addToCart(1);
        cartItemService.cleanCart();
        assert(cartItemService.getCartItems().getCartItems().isEmpty());
    }

    @Test
    void cleanCartWithNoItemsTest() {
        cartItemService.cleanCart();
        assert(cartItemService.getCartItems().getCartItems().isEmpty());
    }
}