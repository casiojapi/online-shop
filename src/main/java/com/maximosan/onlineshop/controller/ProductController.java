package com.maximosan.onlineshop.controller;

import com.maximosan.onlineshop.dto.ProductDTO;
import com.maximosan.onlineshop.service.ProductService;
import com.maximosan.onlineshop.common.ApiResponse;
import com.maximosan.onlineshop.model.Category;
import com.maximosan.onlineshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDTO productDTO) throws Exception {
        Category category = categoryService.findCategoryById(productDTO.getCategoryId());
        productService.createProduct(productDTO, category);
        return new ResponseEntity<>(new ApiResponse(true, "product created"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> getProducts() {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Integer id, @RequestBody ProductDTO productDTO) throws Exception {
        Category category = categoryService.findCategoryById(productDTO.getCategoryId());
        productService.updateProduct(productDTO, id, category);
        return new ResponseEntity<>(new ApiResponse(true, "product updated"), HttpStatus.OK);
    }
}
