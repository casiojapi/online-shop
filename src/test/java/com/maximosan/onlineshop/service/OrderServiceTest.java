package com.maximosan.onlineshop.service;

import com.maximosan.onlineshop.dto.CartDTO;
import com.maximosan.onlineshop.dto.CartItemDTO;
import com.maximosan.onlineshop.dto.OrderDTO;
import com.maximosan.onlineshop.dto.ProductDTO;
import com.maximosan.onlineshop.exception.EmptyCartException;
import com.maximosan.onlineshop.exception.OrderDoesNotExistException;
import com.maximosan.onlineshop.model.Category;
import com.maximosan.onlineshop.model.Order;
import com.maximosan.onlineshop.model.Product;
import com.maximosan.onlineshop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductService productService;
    @Mock
    private CartItemService cartItemService;

    private OrderDTO orderDtoMock;
    private Order orderMock;

    private CartDTO cartMock;
    private Category categoryMock;
    private CartItemDTO cartItemMock;
    private Product productMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

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

        cartItemMock = new CartItemDTO();
        cartItemMock.setId(1);
        cartItemMock.setProduct(productMock);

        cartMock = new CartDTO();
        cartMock.setCartItems(Arrays.asList(cartItemMock));
        cartMock.setTotalCost(100l);

    }

    @Test
    void createOrderFromCartWithItemsTest() {
        when(orderRepository.save(any(Order.class))).thenReturn(orderMock);

        OrderDTO order = orderService.createOrderFromCart(cartMock);

        assertEquals(order.getOrderItemsIds().get(0), productMock.getId());
        assertEquals(order.getPrice(), orderMock.getPrice());
        assertEquals(order.getCreationDate(), orderMock.getCreationDate());
        assertEquals(order.getId(), orderMock.getId());
    }

    @Test
    void getOrderByIdWithExistingOrderTest() {
        when(orderRepository.findById(1)).thenReturn(Optional.of(orderMock));

        OrderDTO res = orderService.getOrderById(1);

        assertEquals(res.getOrderItemsIds().get(0), orderMock.getProducts().get(0));
        assertEquals(res.getPrice(), orderMock.getPrice());
        assertEquals(res.getCreationDate(), orderMock.getCreationDate());
        assertEquals(res.getId(), orderMock.getId());
    }

    @Test
    void getOrderByIdWithNoExistingOrderExceptionTest() {
        when(orderRepository.findById(2)).thenThrow(OrderDoesNotExistException.class);
        assertThrows(OrderDoesNotExistException.class,
                ()->{orderService.getOrderById(2);});
    }

    @Test
    void CreateOrderWithEmptyCartExceptionTest() {
        CartDTO emptyCartMock = new CartDTO();
        emptyCartMock.setCartItems(new ArrayList<>());
        emptyCartMock.setTotalCost(0);

        assertThrows(EmptyCartException.class,
                ()->{orderService.createOrderFromCart(emptyCartMock);});
    }

    @Test
    void BuildListFromOrderTest() {

        ProductDTO productDtoMock = new ProductDTO();
        productDtoMock.setLabel(productMock.getLabel());
        productDtoMock.setWeight(productMock.getWeight());
        productDtoMock.setId(productMock.getId());
        productDtoMock.setPhysical(productMock.isPhysical());
        productDtoMock.setPrice(productMock.getPrice());
        productDtoMock.setDownloadUrl(productMock.getDownloadUrl());
        productDtoMock.setCategoryId(productMock.getCategory().getId());

        when(productService.findProductById(1)).thenReturn(productMock);
        when(productService.getProductDTOFromProduct(productMock)).thenReturn(productDtoMock);

        assertEquals(orderService.buildListFromOrder(orderDtoMock).get(0).getPrice(), productMock.getPrice());
        assertEquals(orderService.buildListFromOrder(orderDtoMock).get(0).isPhysical(), productMock.isPhysical());
        assertEquals(orderService.buildListFromOrder(orderDtoMock).get(0).getLabel(), productMock.getLabel());
        assertEquals(orderService.buildListFromOrder(orderDtoMock).get(0).getCategoryId(), productMock.getCategory().getId());
        assertEquals(orderService.buildListFromOrder(orderDtoMock).get(0).getId(), productMock.getId());
        assertEquals(orderService.buildListFromOrder(orderDtoMock).get(0).getWeight(), productMock.getWeight());
    }
}