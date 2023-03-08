package com.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.ProductDao;
import com.dao.UserDao;
import com.entities.Product;

public class ProductServlet extends HttpServlet {
    public ProductServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDao productDao = new ProductDao();
        List<Product> products = productDao.GetAllProducts(request.getSession().getAttribute("email").toString());
        request.setAttribute("products", products);
        String id = request.getParameter("id");
        String task = request.getParameter("do");
        if (task != null && task.equals("delete")) {
            productDao.DeleteProduct(Integer.parseInt(id));
            request.removeAttribute("id");
            request.removeAttribute("do");
            response.sendRedirect("/Lab5");
            return;
        }

        request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productName = request.getParameter("name");
        String productPrice = request.getParameter("price");

        if (productName.isEmpty() || productPrice.isEmpty()) {
            request.setAttribute("flashMessage", "Please fill all fields");
            request.setAttribute("flashType", "danger");
        } else {
            ProductDao productDao = new ProductDao();
            UserDao userDao = new UserDao();
            Product product = new Product();
            product.setName(productName);
            product.setPrice(productPrice);
            product.setUserId(userDao.GetIdByEmail(request.getSession().getAttribute("email").toString()));
            productDao.AddProduct(product);
            request.setAttribute("flashMessage", "Product added successfully");
            request.setAttribute("flashType", "success");
        }
        doGet(request, response);
    }
}
