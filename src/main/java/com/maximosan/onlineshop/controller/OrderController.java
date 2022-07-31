package com.maximosan.onlineshop.controller;

import com.maximosan.onlineshop.dto.CartDTO;
import com.maximosan.onlineshop.dto.OrderDTO;
import com.maximosan.onlineshop.dto.ProductDTO;
import com.maximosan.onlineshop.service.CartItemService;
import com.maximosan.onlineshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    CartItemService cartItemService;

    @PostMapping()
    public ResponseEntity<OrderDTO> createOrderFromCart() {
        CartDTO cartDTO = cartItemService.getCartItems();
        OrderDTO order = orderService.createOrderFromCart(cartDTO);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<List<ProductDTO>> getProductsFromOrderId(@PathVariable("order-id") Integer id) {
        OrderDTO orderDTO = orderService.getOrderById(id);
        ArrayList<ProductDTO> productList = orderService.buildListFromOrder(orderDTO);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

}
