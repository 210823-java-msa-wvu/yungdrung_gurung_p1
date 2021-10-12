package com.gurung.repositories;

import com.gurung.beans.Designation;
import com.gurung.beans.Employee;
import com.gurung.exceptions.CustomSQLException;
import com.gurung.utils.ConnectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository{

    private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
    private Logger logger = LogManager.getLogger(EmployeeRepository.class);

    // Searching Employee by email
    public Employee SearchingByEmail(String email) {

        try (Connection conn = cu.getConnection()) {

            String sql = "select e.id as employee_id, e.first_name,e.last_name,e.email, e.password, e.username, d.id as designation_id, d.title from employees e inner join designations d on e.designation_id = d.id where  e.email = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Employee emp = new Employee();

                emp.setId(rs.getInt("employee_id"));
                emp.setFirstName(rs.getString("first_name"));
                emp.setLastName(rs.getString("last_name"));
                emp.setEmail(rs.getString("email"));
                emp.setUsername(rs.getString("username"));
                emp.setPassword(rs.getString("password"));

                Designation des = new Designation();
                des.setId(rs.getInt("designation_id"));
                des.setTitle(rs.getString("title"));

                emp.setDesignation(des);

                return emp;
            }
            throw new CustomSQLException("Invalid login Credentials");

        } catch ( SQLException | CustomSQLException  e) {
            logger.trace(e.getMessage());
            System.out.println(e.getMessage());
        }

        return null;
    }

// Add new Employee
    public Employee add(Employee emp) {
        try(Connection conn = cu.getConnection()){

            String sql = "insert into employees values(default, ?, ?, ?, ?, ?, ?) returning *";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, emp.getFirstName());
            ps.setString(2, emp.getLastName());
            ps.setString(3,emp.getEmail());
            ps.setString(4,emp.getPassword());
            ps.setInt(5, emp.getDesignation().getId());
            ps.setString(6,emp.getUsername());


            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                emp.setId(rs.getInt("id"));
                return emp;
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    //Searching by Employee id
    public Employee getById(Integer id) {
        //Try-Connecting to DB
        try(Connection conn = cu.getConnection()){
            String sql = "select e.id as employee_id, e.first_name,e.last_name,e.email, e.username, d.id as designation_id, d.title  from employees e inner join from designations d on e.designation_id = d.id where  e.id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Employee emp = new Employee();

                emp.setId(rs.getInt("employee_id"));
                emp.setFirstName(rs.getString("first_name"));
                emp.setLastName(rs.getString("last_name"));
                emp.setEmail(rs.getString("email"));
                emp.setUsername(rs.getString("username"));
                emp.setPassword(rs.getString("password"));

                Designation des = new Designation();
                des.setId(rs.getInt("designation_id"));
                des.setTitle(rs.getString("title"));

                emp.setDesignation(des);

                return emp;
            }

            throw  new CustomSQLException("Record not found");

        } catch ( SQLException | CustomSQLException e){
            logger.trace(e.getMessage());
            System.out.println(e.getMessage());
        }

        return null;
    }

    // Getting All Employee data
    public List<Employee>getAll() {
        List<Employee> emps = new ArrayList<>();

        try(Connection conn = cu.getConnection()){
            String sql = "select e.id as employee_id, e.first_name, e.last_name, e.email, e.username, d.id  as designation_id , d.title  from employees e inner join designations d on  e.designation_id = d.id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

           if(rs != null){
               while(rs.next()){

                   Employee emp = new Employee();

                   emp.setId(rs.getInt("employee_id"));
                   emp.setFirstName(rs.getString("first_name"));
                   emp.setLastName(rs.getString("last_name"));
                   emp.setEmail(rs.getString("email"));
                   emp.setUsername(rs.getString("username"));

                   Designation des = new Designation();
                   des.setId(rs.getInt("designation_id"));
                   des.setTitle(rs.getString("title"));

                   emp.setDesignation(des);

                   emps.add(emp);

               }
               return emps;
           }
            throw new CustomSQLException("Record Not Found");

        } catch (SQLException | CustomSQLException e){
            logger.trace(e.getStackTrace());
            System.out.println(e.getMessage());
        }

        return null;
    }

    // Update Employee data

    public void update(Employee employee) {

        try(Connection conn = cu.getConnection()){

            String sql = "update employees set first_name = ?, last_name = ? where id = ? ";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setInt(3, employee.getId());

            ps.execute();


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Delete Employee data
    public void delete(Integer integer) {
        try(Connection conn = cu.getConnection()){

            String sql = "delete from employees where id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, integer);
            ps.execute();

            throw new CustomSQLException("Unable to Delete");

        }catch (SQLException | CustomSQLException e){
            logger.trace(e.getStackTrace());
            System.out.println(e.getMessage());
        }
    }
}
