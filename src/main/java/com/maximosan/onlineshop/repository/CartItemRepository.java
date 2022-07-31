package com.maximosan.onlineshop.repository;

import com.maximosan.onlineshop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository  extends JpaRepository<CartItem, Integer> {
    List<CartItem> findAll();
}
