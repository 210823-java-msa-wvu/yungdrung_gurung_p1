package com.gurung.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gurung.beans.Employee;
import com.gurung.services.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutController implements FrontController{
    private Logger logger = LogManager.getLogger(LogoutController.class);
    private EmployeeService employeeService = new EmployeeService();
    private ObjectMapper om = new ObjectMapper();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("userId");

        Cookie[] cookies = request.getCookies();
        System.out.println(cookies);
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getValue());
                cookie.setValue("");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                response.sendRedirect("static/index.html");

            }
            System.out.println(cookies);
        }
        else {
            response.setStatus(401);
            response.sendRedirect("static/login.html");

        }

    }
}
