package org.example;

import org.example.exceptions.DuplicateException;
import org.example.exceptions.ProductNotFoundException;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductRepository repository = new ProductRepository();

        try {
            // Crear productos
            Product product1 = new Product("Laptop", 999.99, "Electronics", "Gaming laptop");
            product1.setStock(10);

            Product product2 = new Product("Mouse", 25.50, "Electronics", "Wireless mouse");
            product2.setStock(50);

            repository.addProduct(product1);
            repository.addProduct(product2);
            System.out.println("Productos agregados exitosamente");

            // Obtener todos los productos
            List<Product> products = repository.getAllProducts();
            System.out.println("\nTodos los productos:");
            for (Product p : products) {
                System.out.println(p);
            }

            // Obtener producto por ID
            Product found = repository.getProductById(1);
            System.out.println("\nProducto encontrado: " + found);

            // Actualizar producto
            found.setPrice(899.99);
            repository.updateProduct(found);
            System.out.println("Producto actualizado");

            // Mostrar productos después de actualizar
            products = repository.getAllProducts();
            System.out.println("\nProductos después de actualizar:");
            for (Product p : products) {
                System.out.println(p);
            }

        } catch (DuplicateException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (ProductNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            // Cerrar Hibernate
            HibernateUtil.shutdown();
        }
    }
}