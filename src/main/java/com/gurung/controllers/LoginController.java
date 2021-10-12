package com.gurung.controllers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
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

public class LoginController implements FrontController {

    private Logger logger = LogManager.getLogger(LoginController.class);
    private EmployeeService employeeService = new EmployeeService();
    private ObjectMapper om = new ObjectMapper();


    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Cookie cookies = null;
        Employee e = om.readValue(request.getReader(), Employee.class);
        String email = e.getEmail();
        String pass = e.getPassword();
//        System.out.println(e);

        Employee loginUser = employeeService.login(email , pass);

        String user = om.writeValueAsString(loginUser);
//        System.out.println(user);

        if (user != null) {

//      HttpSession session = request.getSession();
//      session.setAttribute("user", loginUser);

            cookies = new Cookie("user", user);
            cookies.setMaxAge(24*60*60);
            response.addCookie(cookies);
            response.setStatus(200);
            response.addCookie(cookies);
            response.sendRedirect("static/dashboard.html");

        } else {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid login credentials");
            response.setStatus(401);
            response.sendRedirect("static/login.html");

        }

    }
}
