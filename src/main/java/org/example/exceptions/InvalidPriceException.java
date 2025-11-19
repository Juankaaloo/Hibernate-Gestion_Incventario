package org.example.exceptions;

/**
 * Se lanza cuando se intenta asignar un precio negativo.
 */
public class InvalidPriceException extends RuntimeException {
    public InvalidPriceException(String message) {
        super(message);
    }
}