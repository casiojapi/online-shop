package com.maximosan.onlineshop.repository;

import com.maximosan.onlineshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
