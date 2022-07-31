package maximosan.onlineshop.exception;

public class OrderDoesNotExistException extends IllegalArgumentException {
    public OrderDoesNotExistException(String s) {
        super(s);
    }
}
