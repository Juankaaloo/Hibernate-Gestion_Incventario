package org.example.exceptions;

/**
 * Se lanza cuando se intenta insertar un producto cuyo nombre ya existe.
 */
public class DuplicateException extends RuntimeException {
  public DuplicateException(String message) {
    super(message);
  }
}