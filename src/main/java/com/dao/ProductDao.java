package com.dao;

import java.util.List;

import org.hibernate.Session;

import com.entities.Product;
import com.utils.HibernateUtils;

public class ProductDao {
    public void AddProduct(Product product) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
        session.close();
    }

    public List<Product> GetAllProducts(String email) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        List<Product> products = session.createQuery("from Product where userId = :userId", Product.class)
                .setParameter("userId", new UserDao().GetIdByEmail(email)).getResultList();
        session.getTransaction().commit();
        session.close();
        return products;
    }

    public boolean DeleteProduct(int id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Product product = session.createQuery("from Product where id = :id", Product.class).setParameter("id", id)
                .uniqueResult();
        session.delete(product);
        session.getTransaction().commit();
        session.close();
        return true;
    }
}
