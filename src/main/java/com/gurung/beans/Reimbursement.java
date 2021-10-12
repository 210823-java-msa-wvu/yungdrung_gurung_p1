package com.gurung.beans;

import java.sql.Time;
import java.util.Date;
import java.util.Timer;

public class Reimbursement {
    private Integer id;
    private Date eventStart;
    private Time eventTime;
    private String eventLocation;
    private Event event;
    private String eventDescription;
    private float eventCost;
    private Grade grade;
    private  Employee employee;
    private Date createdAt;
    private Date updatedAt;
    private boolean urgent;

    public Reimbursement() {
    }

    public Reimbursement(Integer id, Grade grade, Date updatedAt) {
        this.id = id;
        this.grade = grade;
        this.updatedAt = updatedAt;
    }

    public Reimbursement(Integer id, Date eventStart, Time eventTime, String eventLocation, Event event, String eventDescription, float eventCost, Grade grade, Employee employee) {
        this.id = id;
        this.eventStart = eventStart;
        this.eventTime = eventTime;
        this.eventLocation = eventLocation;
        this.event = event;
        this.eventDescription = eventDescription;
        this.eventCost = eventCost;
        this.grade = grade;
        this.employee = employee;
    }

    public Reimbursement(Integer id, Date eventStart, Time eventTime, String eventLocation, Event event, String eventDescription, Float eventCost, Grade grade, Employee employee, Date createdAt, Date updatedAt) {
        this.id = id;
        this.eventStart = eventStart;
        this.eventTime = eventTime;
        this.eventLocation = eventLocation;
        this.event = event;
        this.eventDescription = eventDescription;
        this.eventCost = eventCost;
        this.grade = grade;
        this.employee = employee;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Reimbursement(Integer id, Date eventStart, Time eventTime, String eventLocation, Event event, String eventDescription, float eventCost, Grade grade, Employee employee, Date createdAt, Date updatedAt, boolean urgent) {
        this.id = id;
        this.eventStart = eventStart;
        this.eventTime = eventTime;
        this.eventLocation = eventLocation;
        this.event = event;
        this.eventDescription = eventDescription;
        this.eventCost = eventCost;
        this.grade = grade;
        this.employee = employee;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.urgent = urgent;
    }

    public void setEventCost(float eventCost) {
        this.eventCost = eventCost;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getEventStart() {
        return eventStart;
    }

    public void setEventStart(Date eventStart) {
        this.eventStart = eventStart;
    }

    public Time getEventTime() {
        return eventTime;
    }

    public void setEventTime(Time eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Float getEventCost() {
        return eventCost;
    }

    public void setEventCost(Float eventCost) {
        this.eventCost = eventCost;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id=" + id +
                ", eventStart=" + eventStart +
                ", eventTime=" + eventTime +
                ", eventLocation='" + eventLocation + '\'' +
                ", event=" + event +
                ", eventDescription='" + eventDescription + '\'' +
                ", eventCost=" + eventCost +
                ", grade=" + grade +
                ", employee=" + employee +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", urgent=" + urgent +
                '}';
    }
}
