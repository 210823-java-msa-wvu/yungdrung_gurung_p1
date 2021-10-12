package com.gurung.beans;

public class Event {
    private Integer id;
    private String eventName;
    private Integer percent;

    public Event() {
    }

    public Event(Integer id, String eventName) {
        this.id = id;
        this.eventName = eventName;
    }

    public Event(Integer id, Integer percent) {
        this.id = id;
        this.percent = percent;
    }

    public Event(Integer id, String eventName, Integer percent) {
        this.id = id;
        this.eventName = eventName;
        this.percent = percent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", percent=" + percent +
                '}';
    }
}
