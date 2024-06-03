package ru.gb.diploma.model.utils.exceptions;

public class CartItemNotFoundException extends Exception{
    public CartItemNotFoundException(String message) {
        super(message);
    }
}
