package com.maximosan.onlineshop.exception;

public class EmptyCartException  extends IllegalArgumentException {
    public EmptyCartException(String s) {
        super(s);
    }
}
