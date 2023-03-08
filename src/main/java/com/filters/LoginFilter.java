package com.filters;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) arg0;
        HttpServletResponse res = (HttpServletResponse) arg1;

        HttpSession session = req.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("email") != null);

        if (isLoggedIn) {
            if (req.getRequestURI().endsWith("login") || req.getRequestURI().endsWith("register")) {
                res.sendRedirect(req.getContextPath() + "/index");
            } else {
                arg2.doFilter(arg0, arg1);
            }
        } else {
            if (req.getRequestURI().endsWith("login") || req.getRequestURI().endsWith("register")) {
                arg2.doFilter(arg0, arg1);
            } else {
                res.sendRedirect(req.getContextPath() + "/login");
            }
        }
    }

}
