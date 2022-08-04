package com.maximosan.onlineshop.controller;

import com.maximosan.onlineshop.dto.CartDTO;
import com.maximosan.onlineshop.dto.CartItemDTO;
import com.maximosan.onlineshop.dto.OrderDTO;
import com.maximosan.onlineshop.dto.ProductDTO;
import com.maximosan.onlineshop.model.Category;
import com.maximosan.onlineshop.model.Order;
import com.maximosan.onlineshop.model.Product;
import com.maximosan.onlineshop.service.CartItemService;
import com.maximosan.onlineshop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class OrderControllerTest {
    @InjectMocks
    private OrderController orderController;
    @Mock
    private CartItemService cartItemService;
    @Mock
    private OrderService orderService;
    private Category categoryMock;
    private Product productMock;
    private ProductDTO productDtoMock;
    private CartItemDTO cartItemMock;
    private CartDTO cartMock;
    private OrderDTO orderDtoMock;
    private Order orderMock;

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

        orderDtoMock = new OrderDTO();
        orderDtoMock.setId(1);
        orderDtoMock.setCreationDate(LocalDate.now());
        orderDtoMock.setPayed(false);
        orderDtoMock.setOrderItemsIds(Arrays.asList(1));
        orderDtoMock.setPrice(100l);

        orderMock = new Order();
        orderMock.setPayed(false);
        orderMock.setPrice(100l);
        orderMock.setId(1);
        orderMock.setProducts(Arrays.asList(1));
        orderMock.setCreationDate(LocalDate.now());
    }

    @Test
    void createOrderFromCart() {
        when(cartItemService.getCartItems()).thenReturn(cartMock);
        when(orderService.createOrderFromCart(cartMock)).thenReturn(orderDtoMock);

        ResponseEntity<OrderDTO> res = orderController.createOrderFromCart();

        assertEquals(res.getBody().getCreationDate(), orderDtoMock.getCreationDate());
        assertEquals(res.getBody().getOrderItemsIds().get(0), orderDtoMock.getOrderItemsIds().get(0));
        assertEquals(res.getBody().getPrice(), orderDtoMock.getPrice());
        assertEquals(res.getBody().getId(), orderDtoMock.getId());

        assertEquals(res.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getProductsFromOrderId() {
        when(orderService.getOrderById(1)).thenReturn(orderDtoMock);
        ArrayList<ProductDTO> then = new ArrayList<>();
        then.add(productDtoMock);
        when(orderService.buildListFromOrder(orderDtoMock)).thenReturn(then);

        ResponseEntity<List<ProductDTO>> res = orderController.getProductsFromOrderId(1);

        assertEquals(res.getBody().get(0).getPrice(), productDtoMock.getPrice());
        assertEquals(res.getBody().get(0).getLabel(), productDtoMock.getLabel());
        assertEquals(res.getBody().get(0).getId(), productDtoMock.getId());

        assertEquals(res.getStatusCode(), HttpStatus.OK);
    }
}