package com.gurung.repositories;

import com.gurung.beans.*;
import com.gurung.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventRepository implements CrudRepository<Event> {

    private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
    @Override
    public Event add(Event event) {
        return null;
    }

    @Override
    public Event getById(Integer id) {

        //Try-Connecting to DB
        try(Connection conn = cu.getConnection()){
            String sql = "select * from events where id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Event event = new Event();

                event.setId(rs.getInt("id"));
                event.setEventName(rs.getString("event_name"));
                event.setPercent(rs.getInt("percentage"));

                return event;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List getAll() {
        List<Event> events = new ArrayList<>();

        try(Connection conn = cu.getConnection()){
            String sql = "select * from events";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){


                Event event = new Event();

                event.setId(rs.getInt("id"));
                event.setEventName(rs.getString("event_name"));
                event.setPercent(rs.getInt("percentage"));

                events.add(event);

            }
            return events;

        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(Event event) {

    }

    @Override
    public void delete(Integer integer) {

    }
}
