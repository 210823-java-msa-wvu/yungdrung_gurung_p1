package com.gurung.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gurung.beans.Employee;
import com.gurung.services.EmployeeService;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*
 *
 * Endpoints:
 *  /employees - (GET) retrieves all available an employees ->  available only to admin?
 *        - (POST) adds an employee ->  available only to admin?...
 *
 *  /employees/{id} - (GET) gets an employee by id
 *             - (PUT) updates an employee -> want available only to admins?...
 *             - (DELETE) deletes an employee -> want available only to admins?...
 *              - (PATCH) if you want to partially update a resource
 * */


public class EmployeeController implements FrontController {

    private ObjectMapper om = new ObjectMapper();
    private EmployeeService employeeService = new EmployeeService();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = (String) request.getAttribute("path");
        System.out.println("Path attribute: " + path);

        if(path == null || path.equals("")){
            switch (request.getMethod()){
                case "GET": {
                    // may be this is a place where you want to check a user's permission/role
                    System.out.println("Getting all employees ...");
                    List<Employee> employees = employeeService.getAllEmployees();
                    response.getWriter().write(om.writeValueAsString(employees));
                    break;
                }
                case "POST": {
                    // add new employee
                }
            }
        }
        else{

            int empId = Integer.parseInt(path);

            switch (request.getMethod()){

                case "Get": {
                    Employee emp = employeeService.searchEmpById(empId);
                    if(emp != null){
                        response.getWriter().write(om.writeValueAsString(emp));
                    }else {
                        response.sendError(404, "Employee not found");
                    }
                    break;
                }
                case "PUT": {
                    Employee emp = om.readValue(request.getReader(), Employee.class);
                    employeeService.updateEmployee(emp);
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 - server doesn't have any content to send back, but the request is successful
                    break;
                }
                case "DELETE": {
                    employeeService.deleteEmployee(empId);
                    response.setStatus(204);
                    break;
                }
                default:{
                    response.sendError(405);
                    break;
                }
            }
        }


    }
}
