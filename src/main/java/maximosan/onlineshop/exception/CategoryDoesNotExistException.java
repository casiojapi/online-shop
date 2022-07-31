package maximosan.onlineshop.exception;

public class CategoryDoesNotExistException extends IllegalArgumentException {
    public CategoryDoesNotExistException(String s) {
        super(s);
    }
}
