package maximosan.onlineshop.dto;

import maximosan.onlineshop.model.CartItem;
import maximosan.onlineshop.model.Product;

public class CartItemDTO {
    private Integer id;
    private Integer quantity;
    private Product product;

    public CartItemDTO(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CartItemDTO(CartItem cartItem) {
        this.id = cartItem.getId();
        this.quantity = cartItem.getQuantity();
        this.setProduct(cartItem.getProduct());
    }
}
