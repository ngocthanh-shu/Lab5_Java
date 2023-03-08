package com.test;

import java.util.List;

import com.dao.ProductDao;
// import com.dao.UserDao;
import com.entities.Product;
// import com.entities.User;

public class Test {
    public static void main(String[] args) {
        // UserDao userDao = new UserDao();
        ProductDao productDao = new ProductDao();
        List<Product> products = productDao.GetAllProducts("ngocthanh.shu@gmail.com");
        for (Product product : products) {
            System.out.println(product.getName());
        }
    }
}
