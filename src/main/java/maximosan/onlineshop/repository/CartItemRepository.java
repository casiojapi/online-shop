package maximosan.onlineshop.repository;

import maximosan.onlineshop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository  extends JpaRepository<CartItem, Integer> {
    List<CartItem> findAll();
}
