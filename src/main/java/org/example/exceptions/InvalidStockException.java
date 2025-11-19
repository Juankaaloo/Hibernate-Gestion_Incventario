package org.example.exceptions;

/**
 * Se lanza cuando el stock proporcionado es negativo.
 */
public class InvalidStockException extends RuntimeException {
    public InvalidStockException(String message) {
        super(message);
    }
}