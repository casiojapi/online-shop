package maximosan.onlineshop.dto;

import java.time.LocalDate;
import java.util.List;

public class OrderDTO {
    private Integer id;
    private LocalDate creationDate;
    private boolean payed;
    private double price;
    private List<Integer> orderItemsIds;

    public OrderDTO(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Integer> getOrderItemsIds() {
        return orderItemsIds;
    }

    public void setOrderItemsIds(List<Integer> orderItemsIds) {
        this.orderItemsIds = orderItemsIds;
    }
}
