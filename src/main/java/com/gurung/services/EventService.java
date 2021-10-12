package com.gurung.services;

import com.gurung.beans.Event;

import com.gurung.repositories.EventRepository;

import java.util.List;

public class EventService {
    private EventRepository eventRepo = new EventRepository();
    //Searching by ID service
    public Event searchEventById(Integer id){
        Event event = eventRepo.getById(id);

        // check to make sure User object is not null
        if (event != null) {
            // check something like approve or pass

            return  event;
        }
        return null;
    }

    public List<Event> getAllEvents(){
        return eventRepo.getAll();
    }

}
