package com.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.UserDao;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("abc");
        RequestDispatcher rd = request.getRequestDispatcher("jsp/login.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserDao userDao = new UserDao();
        if (userDao.CheckLogin(username, password)) {
            request.getSession().setAttribute("email", username);
            String requestURI = (String) request.getSession().getAttribute("requestURI");
            if (requestURI != null) {
                response.sendRedirect(requestURI);
            } else {
                response.sendRedirect("/Lab5");
            }
        } else {
            // using flash message
            request.setAttribute("flashMessage",
                    "Login failed. Please Check your username or password");
            request.setAttribute("flashType", "error");
            request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
        }
    }
}
