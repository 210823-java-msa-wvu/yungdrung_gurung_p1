package com.gurung.repositories;

import com.gurung.beans.Event;
import com.gurung.beans.Grade;
import com.gurung.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeRepository implements CrudRepository<Grade> {
    private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

    @Override
    public Grade add(Grade o) {
        return null;
    }

    @Override
    public Grade getById(Integer id) {
        //Try-Connecting to DB
        try(Connection conn = cu.getConnection()){
            String sql = "select * from grades where id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Grade grade = new Grade();

                grade.setId(rs.getInt("id"));
                grade.setGradeLabel(rs.getString("grade_label"));

                return grade;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public List getAll() {
    List<Grade> grades = new ArrayList<>();

        try(Connection conn = cu.getConnection()) {
            String sql = "select * from grades";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {


                Grade g = new Grade();

                g.setId(rs.getInt("id"));
                g.setGradeLabel(rs.getString("grade_label"));

                grades.add(g);

            }
            return grades;
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(Grade o) {

    }

    @Override
    public void delete(Integer integer) {

    }
}
