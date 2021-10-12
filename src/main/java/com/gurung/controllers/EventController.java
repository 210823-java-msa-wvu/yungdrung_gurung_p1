package com.gurung.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gurung.beans.Event;
import com.gurung.services.EventService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class EventController implements FrontController{

    private Logger logger = LogManager.getLogger(EventController.class);
    private EventService eventService = new EventService();
    private ObjectMapper om = new ObjectMapper();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = (String) request.getAttribute("path");
        System.out.println("Path attribute: " + path);

        if(path == null || path.equals("")){
            switch (request.getMethod()){
                case "GET": {
                    // may be this is a place where you want to check a user's permission/role
                    System.out.println("Getting all events ...");
                    List<Event> events = eventService.getAllEvents();
                    response.getWriter().write(om.writeValueAsString(events));
                    break;
                }
            }
        }
        else {
            int eventId = Integer.parseInt(path);
            Event e = null;

            switch (request.getMethod()) {

                case "GET": {
                    e = eventService.searchEventById(eventId);
                    if(e != null){
                        response.getWriter().write(om.writeValueAsString(e));
                        System.out.println(e);
                    }else{
                        response.sendError(404, "Event not found");
                    }
                    break;
                }

                default: {
                    response.sendError(405);
                    break;
                }
            }
        }
    }
}
