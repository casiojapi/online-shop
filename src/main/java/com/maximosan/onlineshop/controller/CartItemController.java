package com.maximosan.onlineshop.controller;

import com.maximosan.onlineshop.dto.CartDTO;
import com.maximosan.onlineshop.common.ApiResponse;
import com.maximosan.onlineshop.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    @Autowired
    CartItemService cartItemService;

    @PostMapping("/products/{product-id}")
    public ResponseEntity<ApiResponse> addProductToCart(@PathVariable("product-id") Integer productId) {
        cartItemService.addToCart(productId);
        return new ResponseEntity<>(new ApiResponse(true, "added product to cart"), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<CartDTO> getProductsFromCart() {
        CartDTO allProducts = cartItemService.getCartItems();
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @DeleteMapping("/clean")
    public ResponseEntity<ApiResponse> cleanCart() {
        cartItemService.cleanCart();
        return new ResponseEntity<>(new ApiResponse(true, "your cart is now empty."), HttpStatus.OK);
    }


}
