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

public class FundRepository implements CrudRepository<Fund>{
//    private Logger logger = LogManager.getLogger(FundRepository.class);
    private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
    final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Fund add(Fund fund) {
        System.out.println("reaching Here  in fund Repo...");


        try(Connection conn = cu.getConnection()){
            String sql = "insert into funds values(default, ?, ?, default, ?, ?, ?, ?, ?,?) returning *";
//            System.out.println(fund.getEmployee().getId());
//            System.out.println(fund.getAllocatedFund());
//            System.out.println(fund.getRemainingFund());
//            System.out.println(fund.isStatus());
//            System.out.println(fund.isUrgency());
//            System.out.println(Date.valueOf(dateFormat.format(fund.getCreatedAt())));
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, fund.getEmployee().getId());
            ps.setInt(2, fund.getReimbursement().getId());
            ps.setFloat(3, fund.getAllocatedFund());
            ps.setFloat(4, fund.getRemainingFund());
            ps.setBoolean(5, fund.isStatus());
            ps.setDate(6, Date.valueOf(dateFormat.format(fund.getCreatedAt())));
            ps.setDate(7, Date.valueOf(dateFormat.format(fund.getUpdatedAt())));
            ps.setDate(8, Date.valueOf(dateFormat.format(fund.getResetFundDate())));

            ResultSet rs = ps.executeQuery();

            if(rs != null){
                fund.setId(rs.getInt("id"));
                System.out.println("successfully saved");
                return fund;
            }
            throw new CustomSQLException("Something went wrong!");

        }catch( SQLException | CustomSQLException e){
//            logger.trace(e.getStackTrace());
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Fund getById(Integer id) {
        //Try-Connecting to DB
        try(Connection conn = cu.getConnection()){

            String sql = "select select f.id as id, f.initial_fund, r.id as rId, r.event_description, f.allocated_fund,f.remaining_fund, f.status, f.created_at, f.reset_fund_date, e.id as emId,e.first_name, e.last_name from funds f  right join employees e on f.employee_id = e.id right join reimbursements r on f.reimbursement_id = r.id where f.id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Fund f = new Fund();

                f.setId(rs.getInt("id"));
                f.setInitialFund(rs.getFloat("initial_fund"));
                f.setAllocatedFund(rs.getFloat("allocated_fund"));
                f.setRemainingFund(rs.getFloat("remaining_fund"));
                f.setStatus(rs.getBoolean("status"));
                f.setCreatedAt(rs.getDate("created_at"));
                f.setUpdatedAt(rs.getDate("updated_at"));
                f.setResetFundDate(rs.getDate("reset_fund_date"));

                Employee e = new Employee();
                e.setId(rs.getInt("emId"));
                e.setFirstName(rs.getString("first_name"));
                e.setLastName(rs.getString("last_name"));

                Reimbursement re = new Reimbursement();
                re.setId(rs.getInt("reId"));
                re.setEventDescription(rs.getString("event_description"));

                f.setEmployee(e);
                f.setReimbursement(re);


                return f;
            }

            throw new CustomSQLException("Record not Found");

        } catch ( SQLException | CustomSQLException  e){
//            logger.trace(e.getMessage());
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Fund> getAll() {

        List<Fund> funds = new ArrayList<>();
        //Try-Connecting to DB
        try(Connection conn = cu.getConnection()){

            String sql = "select f.id as id, f.initial_fund, f.allocated_fund, f.remaining_fund, f.status, f.created_at,f.updated_at, f.reset_fund_date, r.id as reId, r.event_description, e.id as emId, e.first_name, e.last_name from funds f inner join employees e on f.employee_id = e.id inner join reimbursements r on f.reimbursement_id = r.id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if(rs != null){

                 while(rs.next()) {
                     Fund f = new Fund();

                    f.setId(rs.getInt("id"));
                    f.setInitialFund(rs.getFloat("initial_fund"));
                    f.setAllocatedFund(rs.getFloat("allocated_fund"));
                    f.setRemainingFund(rs.getFloat("remaining_fund"));
                    f.setStatus(rs.getBoolean("status"));
                    f.setCreatedAt(rs.getDate("created_at"));
                    f.setUpdatedAt(rs.getDate("updated_at"));
                    f.setResetFundDate(rs.getDate("reset_fund_date"));

                    Employee e = new Employee();
                    e.setId(rs.getInt("emId"));
                    e.setFirstName(rs.getString("first_name"));
                    e.setLastName(rs.getString("last_name"));

                    Reimbursement re = new Reimbursement();
                    re.setId(rs.getInt("reId"));
                    re.setEventDescription(rs.getString("event_description"));

                    f.setEmployee(e);
                    f.setReimbursement(re);


                    funds.add(f);
                 }
                throw new CustomSQLException("Record not Found");
            }
            return  funds;
        } catch ( SQLException | CustomSQLException e){
//            logger.trace(e.getStackTrace());
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void update(Fund fund) {

        try(Connection conn = cu.getConnection()){
            // TODO : logic multiple option of update...

            String sql = "update funds set allocated_fund = ?, remaining_fund = ? , status = ? updated_at = ? where id = ? ";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setFloat(1, fund.getAllocatedFund());
            ps.setFloat(2, fund.getRemainingFund());
            ps.setBoolean(3, fund.isStatus());
            ps.setDate(5, Date.valueOf(dateFormat.format(fund.getUpdatedAt())));
//            ps.setDate(8, Date.valueOf(dateFormat.format(fund.getResetFundDate())));

            ps.execute();


        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Integer id) {
        try(Connection conn = cu.getConnection()){

            String sql = "delete from funds where id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
