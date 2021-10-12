package com.gurung.repositories;

import com.gurung.beans.Employee;
import com.gurung.beans.Event;
import com.gurung.beans.Grade;
import com.gurung.beans.Reimbursement;
import com.gurung.exceptions.CustomSQLException;
import com.gurung.utils.ConnectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementRepository{
    private Logger logger = LogManager.getLogger(ReimbursementRepository.class);
    private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
    final  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    // Add new Reimbursement
    public Reimbursement add(Reimbursement reimbursement) {

//
//        System.out.println(Date.valueOf(dateFormat.format(reimbursement.getEventStart())));
//        System.out.println(reimbursement.getEventCost());

        try(Connection conn = cu.getConnection()){

            String sql = "insert into reimbursements values(default, ?, ?, ?, ?, ?, ?, default, ?, ?, ?, ?) returning *";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(dateFormat.format(reimbursement.getEventStart())));
            ps.setTime(2, reimbursement.getEventTime());
            ps.setString(3, reimbursement.getEventLocation());
            ps.setInt(4,reimbursement.getEvent().getId());
            ps.setString(5,reimbursement.getEventDescription());
            ps.setFloat(6, reimbursement.getEventCost());
            ps.setInt(7, reimbursement.getEmployee().getId());
            ps.setDate(8,Date.valueOf(dateFormat.format(reimbursement.getCreatedAt())));
            ps.setDate(9,Date.valueOf(dateFormat.format(reimbursement.getUpdatedAt())));
            ps.setBoolean(10, reimbursement.isUrgent());


            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                reimbursement.setId(rs.getInt("id"));
                return reimbursement;
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    //Searching reimbursement by id
    public Reimbursement getById(Integer id) {
        //Try-Connecting to DB
        try(Connection conn = cu.getConnection()){

            String sql = "select r.id as id, r.event_start, r.event_time,r.event_location, r.event_cost, r.created_at, r.urgent, r.event_description, r.updated_at, e.id as eId ,e.event_name as event_label, e.percentage, g.grade_label ,emp.id as emp_id, emp.first_name, emp.last_name from reimbursements r  left join events e on r.event_id = e.id  left join employees emp on r.employee_id = emp.id left join grades g on r.grade_id = g.id where r.id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Reimbursement re = new Reimbursement();

                re.setId(rs.getInt("id"));
                re.setEventStart(rs.getDate("event_start"));
                re.setEventTime(rs.getTime("event_time"));
                re.setEventLocation(rs.getString("event_location"));
                re.setEventCost(rs.getFloat("event_cost"));
                re.setCreatedAt(rs.getDate("created_at"));
                re.setUpdatedAt(rs.getDate("updated_at"));
                re.setEventDescription(rs.getString("event_description"));
                re.setUrgent(rs.getBoolean("urgent"));

                Employee emp = new Employee();
                emp.setId(rs.getInt("emp_id"));
                emp.setFirstName(rs.getString("first_name"));
                emp.setLastName(rs.getString("last_name"));

                Event e = new Event();
                e.setId(rs.getInt("eId"));
                e.setEventName(rs.getString("event_label"));
                e.setPercent(rs.getInt("percentage"));

                Grade g = new Grade();
                g.setGradeLabel(rs.getString("grade_label"));

                re.setEvent(e);
                re.setGrade(g);
                re.setEmployee(emp);

                return re;
            }

            throw new CustomSQLException("Record not Found");

        } catch ( CustomSQLException | SQLException e){
            logger.trace(e.getMessage());
            System.out.println(e.getMessage());
            return null;
        }


    }

    // Getting All Reimbursements table data
    public List<Reimbursement>getAll() {
        List<Reimbursement> reimbs = new ArrayList<>();

        try(Connection conn = cu.getConnection()){
            String sql = "select r.id as id, r.event_start, r.event_time,r.event_location,r.urgent, r.event_cost, r.created_at, r.event_description, r.updated_at, e.event_name as event_label, g.grade_label ,emp.id as emp_id, emp.first_name, emp.last_name from reimbursements r left join events e on r.event_id = e.id left join employees emp on r.employee_id = emp.id left join grades g on r.grade_id = g.id order by r.id desc";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){


                Reimbursement reimb = new Reimbursement();

                reimb.setId(rs.getInt("id"));
                reimb.setEventStart(rs.getDate("event_start"));
                reimb.setEventTime(rs.getTime("event_time"));
                reimb.setEventLocation(rs.getString("event_location"));
                reimb.setEventCost(rs.getFloat("event_cost"));
                reimb.setCreatedAt(rs.getDate("created_at"));
                reimb.setUpdatedAt(rs.getDate("updated_at"));
                reimb.setEventDescription(rs.getString("event_description"));
                reimb.setUrgent(rs.getBoolean("urgent"));


                Employee emp = new Employee();
                emp.setId(rs.getInt("emp_id"));
                emp.setFirstName(rs.getString("first_name"));
                emp.setLastName(rs.getString("last_name"));

                Event e = new Event();
                e.setEventName(rs.getString("event_label"));

                Grade g = new Grade();
                g.setGradeLabel(rs.getString("grade_label"));

                reimb.setEvent(e);
                reimb.setGrade(g);
                reimb.setEmployee(emp);

                reimbs.add(reimb);

            }
            return reimbs;

        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    // Update reimbursements table  data

    public void update(Reimbursement reimbursement) {
        System.out.println("from repository");
        System.out.println(reimbursement.getGrade().getId());
        System.out.println(reimbursement.getId());
        System.out.println(reimbursement.getUpdatedAt());


        try(Connection conn = cu.getConnection()){

            String sql = "update reimbursements set grade_id = ?, updated_at = ? where id = ? ";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, reimbursement.getGrade().getId());
            ps.setDate(2, Date.valueOf(dateFormat.format(reimbursement.getUpdatedAt())));
            ps.setInt(3, reimbursement.getId());

            ps.execute();


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Delete data  from Reimbursements table
    public void delete(Integer integer) {
        try(Connection conn = cu.getConnection()){

            String sql = "delete from reimbursements where id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, integer);
            ps.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
