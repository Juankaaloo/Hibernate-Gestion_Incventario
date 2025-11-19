package org.example;

import org.example.exceptions.InvalidPriceException;
import org.example.exceptions.InvalidStockException;

import javax.persistence.*;



/**
 * Entidad Product que representa un registro de la tabla "products" en la base de datos.
 * Utiliza anotaciones de JPA/Hibernate para mapear campos y restricciones.
 */
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // El ID se genera automáticamente en BD
    private int id;

    @Column(nullable = false, unique = true) // No puede ser null y debe ser único
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int stock = 0;

    private String category;

    private String description;

    // Constructor vacio requerido por Hibernate
    public Product() {
    }

    /**
     * Constructor principal para crear productos.
     * Valida nombre y precio antes de asignarlos.
     */
    public Product(String name, double price, String category, String description) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (price < 0) {
            throw new InvalidPriceException("El precio no puede ser negativo");
        }

        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
    }

    /**
     * Constructor alternativo que permite asignar ID y stock.
     * Reutiliza el constructor anterior para validación.
     */
    public Product(int id, String name, double price, int stock, String category, String description) {
        this(name, price, category, description);
        this.id = id;
        this.stock = stock;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }

    /**
     * Valida que el precio nunca sea negativo.
     */
    public void setPrice(double price) {
        if (price < 0) throw new InvalidPriceException("El precio no puede ser negativo");
        this.price = price;
    }

    public int getStock() { return stock; }

    /**
     * Valida que el stock nunca sea negativo.
     */
    public void setStock(int stock) {
        if (stock < 0) throw new InvalidStockException("El stock no puede ser negativo");
        this.stock = stock;
    }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return String.format(
                "Product{id=%d, name='%s', price=%.2f, stock=%d, category='%s', description='%s'}",
                id, name, price, stock, category, description
        );
    }
}