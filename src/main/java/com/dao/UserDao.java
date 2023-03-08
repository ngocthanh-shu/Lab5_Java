package com.dao;

import org.hibernate.Session;

import com.entities.User;
import com.utils.HibernateUtils;

public class UserDao {
    public UserDao() {
    }

    public boolean CheckExistEmail(String email) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        User user = session.createQuery("from User where email = :email", User.class)
                .setParameter("email", email).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return user != null;
    }

    public boolean CheckLogin(String email, String password) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        User user = session.createQuery("from User where email = :email", User.class)
                .setParameter("email", email).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return user != null && user.getPassword().equals(password);
    }

    public boolean Register(User user) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public int GetIdByEmail(String email) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        User user = session.createQuery("from User where email = :email", User.class)
                .setParameter("email", email).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return user.getId();
    }
}
