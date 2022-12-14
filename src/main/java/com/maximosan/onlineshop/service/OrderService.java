package com.maximosan.onlineshop.service;

import com.maximosan.onlineshop.dto.CartDTO;
import com.maximosan.onlineshop.dto.CartItemDTO;
import com.maximosan.onlineshop.dto.OrderDTO;
import com.maximosan.onlineshop.dto.ProductDTO;
import com.maximosan.onlineshop.exception.EmptyCartException;
import com.maximosan.onlineshop.exception.OrderDoesNotExistException;
import com.maximosan.onlineshop.model.Order;
import com.maximosan.onlineshop.model.Product;
import com.maximosan.onlineshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    CartItemService cartItemService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderRepository orderRepository;

    public OrderDTO createOrderFromCart(CartDTO cartDTO) {
        if (cartDTO.getCartItems().isEmpty()) {
            throw new EmptyCartException("you cannot place an order if your cart is empty.");
        }

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setPayed(false);
        orderDTO.setPrice(cartDTO.getTotalCost());
        orderDTO.setCreationDate(LocalDate.now());


        ArrayList<Integer> orderItemsIds = new ArrayList<>();

        for (CartItemDTO item: cartDTO.getCartItems()) {
            orderItemsIds.add(item.getProduct().getId());
        }

        orderDTO.setOrderItemsIds(orderItemsIds);
        cartItemService.cleanCart();
        Order order = getOrderFromDTO(orderDTO);
        order = orderRepository.save(order);
        orderDTO.setId(order.getId());
        return orderDTO;
    }

    private Order getOrderFromDTO(OrderDTO orderDTO) {
        Order order = new Order();
        order.setCreationDate(orderDTO.getCreationDate());
        order.setPayed(orderDTO.isPayed());
        order.setPrice(orderDTO.getPrice());
        order.setProducts(orderDTO.getOrderItemsIds());
        return order;
    }

    private OrderDTO getOrderDTOFromOrder(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setCreationDate(order.getCreationDate());
        dto.setPayed(order.isPayed());
        dto.setPrice(order.getPrice());
        dto.setOrderItemsIds(order.getProducts());
        dto.setId(order.getId());
        return dto;
    }

    public OrderDTO getOrderById(Integer id) throws OrderDoesNotExistException {

        Optional<Order> wrappedOrder = orderRepository.findById(id);
        if (!wrappedOrder.isPresent()) {
            throw new OrderDoesNotExistException("order with id: " + id + " does not exist.");
        }
        Order order = wrappedOrder.get();
        OrderDTO dto = getOrderDTOFromOrder(order);
        return dto;
    }


    public ArrayList<ProductDTO> buildListFromOrder(OrderDTO orderDTO) {
        ArrayList<ProductDTO> productList = new ArrayList<>();
        for (Integer productId: orderDTO.getOrderItemsIds()) {
            Product prod = productService.findProductById(productId);
            ProductDTO prodDTO = productService.getProductDTOFromProduct(prod);
            productList.add(prodDTO);
        }
        return productList;
    }
}
