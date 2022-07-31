package com.maximosan.onlineshop.exception;

public class ProductDoesNotExistException extends IllegalArgumentException {
    public ProductDoesNotExistException(String s) {
        super(s);
    }
}
