package com.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.UserDao;
import com.entities.User;

public class RegisterServlet extends HttpServlet {
    public RegisterServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("jsp/register.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("password-confirm");
        System.out.println(fullname + " " + email + " " + password + " " + confirmPassword + " ");

        if (fullname.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            request.setAttribute("flashMessage", "Please fill all fields");
            request.setAttribute("flashType", "error");
            request.getRequestDispatcher("jsp/register.jsp").forward(request, response);
            return;
        } else if (!password.equals(confirmPassword)) {
            request.setAttribute("flashMessage", "Password and Confirm Password must be the same");
            request.setAttribute("flashType", "error");
            request.getRequestDispatcher("jsp/register.jsp").forward(request, response);
            return;
        } else if (email.contains("@") == false) {
            request.setAttribute("flashMessage", "Email must be valid");
            request.setAttribute("flashType", "error");
            request.getRequestDispatcher("jsp/register.jsp").forward(request, response);
            return;
        } else if (password.length() < 8) {
            request.setAttribute("flashMessage", "Password must be at least 8 characters");
            request.setAttribute("flashType", "error");
            request.getRequestDispatcher("jsp/register.jsp").forward(request, response);
            return;
        } else {
            UserDao userDao = new UserDao();
            if (userDao.CheckExistEmail(email)) {
                request.setAttribute("flashMessage", "Email already exists");
                request.setAttribute("flashType", "error");
                request.getRequestDispatcher("jsp/register.jsp").forward(request, response);
                return;
            } else {
                User user = new User();
                user.setFullname(fullname);
                user.setEmail(email);
                user.setPassword(password);
                userDao.Register(user);
                request.getSession().setAttribute("email", email);
                response.sendRedirect("/Lab5");
            }
        }
    }
}
