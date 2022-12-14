package com.maximosan.onlineshop.service;

import com.maximosan.onlineshop.dto.CartDTO;
import com.maximosan.onlineshop.dto.CartItemDTO;
import com.maximosan.onlineshop.model.CartItem;
import com.maximosan.onlineshop.repository.CartItemRepository;
import com.maximosan.onlineshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemService {
    @Autowired
    ProductService productService;

    @Autowired
    CartItemRepository cartItemRepository;
    public void addToCart(Integer productId) {
        Product product = productService.findProductById(productId);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCreatedDate(LocalDate.now());
        cartItem.setQuantity(1);

        cartItemRepository.save(cartItem);
    }

    public CartDTO getCartItems() {
        List<CartItem> cartItems = cartItemRepository.findAll();

        List<CartItemDTO> cartItemDTOs = new ArrayList<>();

        double cost = 0;

        for (CartItem cartItem: cartItems) {
            CartItemDTO cartItemDTO = new CartItemDTO(cartItem);
            cartItemDTOs.add(cartItemDTO);
            cost += cartItemDTO.getQuantity() * cartItemDTO.getProduct().getPrice();
        }

        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartItems(cartItemDTOs);
        cartDTO.setTotalCost(cost);

        return cartDTO;
    }

    public void cleanCart() {
        cartItemRepository.deleteAll();
    }
}
