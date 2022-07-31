package maximosan.onlineshop.repository;

import maximosan.onlineshop.model.CartItem;
import maximosan.onlineshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
