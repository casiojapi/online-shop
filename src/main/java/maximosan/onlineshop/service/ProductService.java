package maximosan.onlineshop.service;

import maximosan.onlineshop.dto.ProductDTO;
import maximosan.onlineshop.exception.ProductDoesNotExistException;
import maximosan.onlineshop.model.Category;
import maximosan.onlineshop.model.Product;
import maximosan.onlineshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public void createProduct(ProductDTO productDTO, Category category) {
        Product product = getProductFromProductDTO(productDTO, category);
        productRepository.save(product);
    }

    private Product getProductFromProductDTO(ProductDTO productDTO, Category category) {
        Product product = new Product();
        product.setLabel(productDTO.getLabel());
        product.setPhysical(productDTO.isPhysical());
        product.setDownloadUrl(productDTO.getDownloadUrl());
        product.setWeight(productDTO.getWeight());
        product.setPrice(productDTO.getPrice());
        product.setCategory(category);
        product.setId(productDTO.getId());
        return product;
    }
    public ProductDTO getProductDTOFromProduct(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setLabel(product.getLabel());
        dto.setPhysical(product.isPhysical());
        dto.setDownloadUrl(product.getDownloadUrl());
        dto.setWeight(product.getWeight());
        dto.setPrice(product.getPrice());
        dto.setCategoryId(product.getCategory().getId());
        dto.setId(product.getId());
        return dto;
    }
    public List<ProductDTO> getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> dtos = new ArrayList<>();
        for (Product prod: products) {
            dtos.add(getProductDTOFromProduct(prod));
        }
        return dtos;
    }

    public void updateProduct(ProductDTO productDTO, int id, Category category) throws ProductDoesNotExistException {
        Optional<Product> wrappedProduct = productRepository.findById(id);
        if (!wrappedProduct.isPresent()) {
            throw new ProductDoesNotExistException("product with id: " + id + " does not exist.");
        }
        Product product = wrappedProduct.get();
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setPhysical(productDTO.isPhysical());
        product.setDownloadUrl(productDTO.getDownloadUrl());
        product.setLabel(productDTO.getLabel());
        product.setCategory(category);
        productRepository.save(product);
    }

    public Product findProductById(Integer productId) throws ProductDoesNotExistException {
        Optional<Product> wrappedProduct = productRepository.findById(productId);
        if (!wrappedProduct.isPresent()) {
            throw new ProductDoesNotExistException("product with id: " + productId + " does not exist.");
        }
        return wrappedProduct.get();
    }
}
