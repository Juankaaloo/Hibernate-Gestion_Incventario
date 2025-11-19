package org.example;

import org.example.exceptions.DuplicateException;
import org.example.exceptions.ProductNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * Clase utilitaria que centraliza la configuración de Hibernate.
 * Incluye métodos CRUD (Crear, Leer, Actualizar, Eliminar) para la entidad Product.
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    /**
     * Construye el SessionFactory leyendo el archivo hibernate.cfg.xml.
     */
    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Cierra el SessionFactory cuando ya no se necesita.
     */
    public static void shutdown() {
        getSessionFactory().close();
    }

    /**
     * Agrega un nuevo producto a la base de datos.
     * Maneja errores como clave duplicada.
     */
    public static Product addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
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

    /**
     * Busca un producto por ID.
     */
    public static Product getProductById(int id) {
        try (Session session = sessionFactory.openSession()) {
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


    /**
     * Obtiene la lista completa de productos almacenados.
     */
    public static List<Product> getAllProducts() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Product", Product.class).list();
        } catch (Exception e) {
            System.err.println("Error al obtener todos los productos: " + e.getMessage());
            return null;
        }
    }

    /**
     * Actualiza un producto existente.
     */
    public static boolean updateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
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

    /**
     * Elimina un producto según su ID.
     */
    public static boolean deleteProduct(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
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

    /**
     * Elimina todos los productos de la tabla.
     */
    public static boolean deleteAllProducts() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM Product").executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error al eliminar todos los productos: " + e.getMessage());
            return false;
        }
    }
}