package org.example;

import org.example.exceptions.DuplicateException;
import org.example.exceptions.ProductNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class ProductRepository {

    public Product addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(product);
            transaction.commit();
            return product;
        } catch (ConstraintViolationException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DuplicateException("El producto con el mismo nombre ya existe en la base de datos.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error al agregar producto: " + e.getMessage());
            return null;
        }
    }

    public Product getProductById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Product product = session.get(Product.class, id);
            if (product == null) {
                throw new ProductNotFoundException("No se encontró ningún producto con ID: " + id);
            }
            return product;
        } catch (Exception e) {
            System.err.println("Error al buscar producto por ID: " + e.getMessage());
            return null;
        }
    }

    public List<Product> getAllProducts() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Product", Product.class).list();
        } catch (Exception e) {
            System.err.println("Error al obtener todos los productos: " + e.getMessage());
            return null;
        }
    }

    public boolean updateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(product);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteProduct(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            if (product == null) {
                throw new ProductNotFoundException("No se encontró ningún producto con ID: " + id);
            }
            session.delete(product);
            transaction.commit();
            return true;
        } catch (ProductNotFoundException e) {
            throw e;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
}