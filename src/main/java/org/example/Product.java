package org.example;

import org.example.exceptions.InvalidPriceException;
import org.example.exceptions.InvalidStockException;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int stock = 0;

    private String category;

    private String description;

    // Constructor por defecto requerido por Hibernate
    public Product() {
    }

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
    public void setPrice(double price) {
        if (price < 0) throw new InvalidPriceException("El precio no puede ser negativo");
        this.price = price;
    }

    public int getStock() { return stock; }
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