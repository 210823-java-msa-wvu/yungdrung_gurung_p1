package com.gurung.repositories;

import com.gurung.beans.*;
import com.gurung.exceptions.CustomSQLException;
import com.gurung.utils.ConnectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ApprovalRepository implements CrudRepository<Approval> {
    private Logger logger = LogManager.getLogger(FundRepository.class);
    private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
    final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Approval add(Approval approval) {
        System.out.println("reaching repo...");
        try(Connection conn = cu.getConnection()){

            String sql = "insert into approvals values(default, ?, ?, ?, ?, ?) returning *";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1,approval.getReason());
            ps.setInt(2, approval.getReimbursement().getId());
            ps.setInt(3, approval.getEmployee().getId());
            ps.setBoolean(4, approval.isStatus());
            ps.setDate(5, Date.valueOf(dateFormat.format(approval.getStartDate())));

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                approval.setId(rs.getInt("id"));
                return approval;
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Approval getById(Integer id) {
        //Try-Connecting to DB
        try(Connection conn = cu.getConnection()){

            String sql = "select a.id as id, a.reason, a.employee_id as approved_by, emp1.first_name as a_first, emp1.last_name as a_last, d1.title as a_title, a.status, a.approved_at, r.event_start as start_at, r.event_location, r.employee_id as request_by, emp2.first_name as r_first, emp2.last_name as r_last, d2.title as r_title, r.id as r_id, r.event_time,r.event_location, r.event_cost, r.event_description, e.event_name as event_label, g.grade_label from approvals a  left join employees emp1 on a.employee_id = emp1.id left join designations d1 on emp1.designation_id = d1.id full join reimbursements r on a.reimbursement_id= r.id left join employees emp2 on r.employee_id = emp2.id left join events e on r.event_id = e.id left join grades g on r.grade_id = g.id left join designations d2 on emp2.designation_id = d2.id where a.id =?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Approval app = new Approval();

                app.setId(rs.getInt("id"));
                app.setReason(rs.getString("reason"));
                app.setStatus(rs.getBoolean("status"));
                app.setStartDate(rs.getDate("approved_at"));

                Employee emp1 = new Employee();
                emp1.setId(rs.getInt("approved_by"));
                emp1.setFirstName(rs.getString("a_first"));
                emp1.setLastName(rs.getString("a_last"));

                Designation des1 = new Designation();
                des1.setTitle(rs.getString("a_title"));

                emp1.setDesignation(des1);

                app.setEmployee(emp1);

                Reimbursement re = new Reimbursement();
                re.setId(rs.getInt("r_id"));
                re.setEventStart(rs.getDate("start_at"));
                re.setEventCost(rs.getFloat("event_cost"));
                re.setEventDescription(rs.getString("event_description"));
                re.setEventLocation(rs.getString("event_location"));

                Employee emp2 = new Employee();
                emp2.setId(rs.getInt("request_by"));
                emp2.setFirstName(rs.getString("r_first"));
                emp2.setLastName(rs.getString("r_last"));

                Designation des2 = new Designation();
                des2.setTitle(rs.getString("r_title"));

                emp1.setDesignation(des2);

                re.setEmployee(emp2);

                Event e = new Event();
                e.setEventName(rs.getString("event_label"));

                re.setEvent(e);

                Grade g = new Grade();
                g.setGradeLabel(rs.getString("grade_label"));

                re.setGrade(g);

                app.setReimbursement(re);

                app.setReimbursement(re);


                return app;
            }

            throw new CustomSQLException("Record not Found");

        } catch ( CustomSQLException | SQLException e){
            logger.trace(e.getMessage());
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Approval> getAll() {
        List<Approval> as = new ArrayList<>();
        String sql = "select a.id as id, a.reason, a.employee_id as approved_by, emp1.first_name as a_first, emp1.last_name as a_last, d1.title as a_title, a.status, a.approved_at, r.event_start as start_at, r.event_location, r.employee_id as request_by, emp2.first_name as r_first, emp2.last_name as r_last, d2.title as r_title, r.id as r_id, r.event_time,r.event_location, r.event_cost, r.event_description, e.event_name as event_label, g.grade_label from project1.approvals a  left join project1.employees emp1 on a.employee_id = emp1.id inner join project1.designations d1 on emp1.designation_id = d1.id right join project1.reimbursements r on a.reimbursement_id= r.id left join project1.employees emp2 on r.employee_id = emp2.id left join project1.events e on r.event_id = e.id left join project1.grades g on r.grade_id = g.id left join project1.designations d2 on emp2.designation_id = d2.id order by r.id desc";
        try(Connection conn = cu.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){


                Approval app = new Approval();

                app.setId(rs.getInt("id"));
                app.setReason(rs.getString("reason"));
                app.setStatus(rs.getBoolean("status"));
                app.setStartDate(rs.getDate("approved_at"));

                Employee emp1 = new Employee();
                emp1.setId(rs.getInt("approved_by"));
                emp1.setFirstName(rs.getString("a_first"));
                emp1.setLastName(rs.getString("a_last"));

                Designation des1 = new Designation();
                des1.setTitle(rs.getString("a_title"));

                emp1.setDesignation(des1);

                app.setEmployee(emp1);

                Reimbursement re = new Reimbursement();
                re.setId(rs.getInt("r_id"));
                re.setEventStart(rs.getDate("start_at"));
                re.setEventTime(rs.getTime("event_time"));
                re.setEventCost(rs.getFloat("event_cost"));
                re.setEventDescription(rs.getString("event_description"));
                re.setEventLocation(rs.getString("event_location"));


                Employee emp2 = new Employee();
                emp2.setId(rs.getInt("request_by"));
                emp2.setFirstName(rs.getString("r_first"));
                emp2.setLastName(rs.getString("r_last"));

                Designation des2 = new Designation();
                des2.setTitle(rs.getString("r_title"));

                emp2.setDesignation(des2);

                re.setEmployee(emp2);

                Event e = new Event();
                e.setEventName(rs.getString("event_label"));

                re.setEvent(e);

                Grade g = new Grade();
                g.setGradeLabel(rs.getString("grade_label"));

                re.setGrade(g);

                app.setReimbursement(re);

                app.setReimbursement(re);

                as.add(app);

            }
            return as;

        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(Approval a) {
        try(Connection conn = cu.getConnection()){
            // TODO : logic multiple option of update...

            String sql = "update approvals set description = ?, reimbursement_id = ?, employee_id = ? , status = ? start_date = ? where id = ? ";

            PreparedStatement ps = conn.prepareStatement(sql);


            ps.setString(1, a.getReason());
            ps.setObject(2,a.getReimbursement().getId());
            ps.setObject(3,a.getEmployee().getId());
            ps.setBoolean(4, a.isStatus());
            ps.setDate(5, Date.valueOf(dateFormat.format(a.getStartDate())));
            ps.setInt(6, a.getId());

            ps.execute();


        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Integer id) {
        try(Connection conn = cu.getConnection()){

            String sql = "delete from approvals where id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
