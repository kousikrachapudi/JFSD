package com.learn.mycart.dao;

import com.learn.mycart.entities.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private final SessionFactory factory;

    public ProductDao(SessionFactory factory) {
        this.factory = factory;
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        Transaction transaction = null; // Transaction for better handling
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction(); // Begin transaction
            productList = session.createQuery("from Product", Product.class).list();
            transaction.commit(); // Commit transaction
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); // Rollback on error
            e.printStackTrace(); // Log the exception
        }
        return productList;
    }

    public List<Product> getAllProductsById(int categoryId) {
        List<Product> productList = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction(); // Begin transaction
            Query<Product> query = session.createQuery("from Product p where p.category.categoryId = :cid", Product.class);
            query.setParameter("cid", categoryId);
            productList = query.list();
            transaction.commit(); // Commit transaction
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); // Rollback on error
            e.printStackTrace(); // Log the exception
        }
        return productList;
    }

    public void saveProduct(Product p) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(p);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace(); // Log the exception
        }
    }

    // Other methods...
}
