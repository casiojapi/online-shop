package maximosan.onlineshop.controller;

import io.swagger.models.Response;
import maximosan.onlineshop.common.ApiResponse;
import maximosan.onlineshop.dto.ProductDTO;
import maximosan.onlineshop.model.Category;
import maximosan.onlineshop.model.Product;
import maximosan.onlineshop.repository.CategoryRepository;
import maximosan.onlineshop.repository.ProductRepository;
import maximosan.onlineshop.service.CategoryService;
import maximosan.onlineshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
