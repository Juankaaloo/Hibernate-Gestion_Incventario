package org.example.exceptions;

/**
 * Se lanza cuando un producto no existe en la base de datos.
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}