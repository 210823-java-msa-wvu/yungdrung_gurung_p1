package com.gurung.services;

import com.gurung.beans.Employee;
import com.gurung.repositories.EmployeeRepository;

import java.util.List;

public class EmployeeService {

    private static EmployeeRepository employeeRepo = new EmployeeRepository();


    public Employee searchEmpById(Integer id){
        Employee emp = employeeRepo.getById(id);

        // check to make sure User object is not null
        if (emp != null) {
            // now check to make sure it matches
//            if (id == emp.getId() && emp.getDesignation().getTitle().equals("HR")) {
//                return emp;
//            }
            return  emp;
        }
        return null;
    }

    public Employee login(String email, String password) {

        Employee emp = employeeRepo.SearchingByEmail(email); // more of the Sole Responsibility Principle at work

        // check to make sure User object is not null
        if (emp != null) {
            // now check to make sure it matches
            if (email.equals(emp.getEmail()) && password.equals(emp.getPassword())) {
                return emp;
            }
        }
        return null;
    }

//    public Employee login(String email, String password) {
//
//        Employee emp = employeeRepo.SearchingByEmail(email); // more of the Sole Responsibility Principle at work
//
//        // check to make sure User object is not null
//        if (emp != null) {
//            // now check to make sure it matches
//            if (email.equals(emp.getEmail()) && password.equals(emp.getPassword())) {
//                return emp;
//            }
//        }
//        return null;
//    }

    public List<Employee> getAllEmployees(){
        return employeeRepo.getAll();
    }

//    public boolean updateEmployee(Integer id, String firstName,String lastName){
    public boolean updateEmployee(Employee employee){
        Employee emp = employeeRepo.getById(employee.getId());

        if(emp.getId() == employee.getId()){
            emp.setId(employee.getId());
            emp.setFirstName(employee.getFirstName());
            emp.setLastName(employee.getLastName());

            employeeRepo.update(emp);
            return true;
        }
        return false;
    }

    public boolean deleteEmployee(Integer id){
        Employee employee = employeeRepo.getById(id);

        if(employee.getId() == id){
            employeeRepo.delete(id);
            return true;

        }
        return false;
    }
}
