package com.gurung.beans;

import java.util.Date;

public class Approval {
    private  Integer id;
    private String reason;
    private Employee employee;
    private Reimbursement reimbursement;
    private boolean status;
    private Date startDate;

    public Approval() {
    }

    public Approval(String reason, Employee employee, Reimbursement reimbursement, boolean status) {
        this.reason = reason;
        this.employee = employee;
        this.reimbursement = reimbursement;
        this.status = status;
    }

    public Approval(String reason, Employee employee, Reimbursement reimbursement, boolean status, Date startDate) {
        this.reason = reason;
        this.employee = employee;
        this.reimbursement = reimbursement;
        this.status = status;
        this.startDate = startDate;
    }

    public Approval(Integer id, String reason, Employee employee, Reimbursement reimbursement, boolean status, Date startDate) {
        this.id = id;
        this.reason = reason;
        this.employee = employee;
        this.reimbursement = reimbursement;
        this.status = status;
        this.startDate = startDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Reimbursement getReimbursement() {
        return reimbursement;
    }

    public void setReimbursement(Reimbursement reimbursement) {
        this.reimbursement = reimbursement;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Approval{" +
                "id=" + id +
                ", reason='" + reason + '\'' +
                ", employee=" + employee +
                ", reimbursement=" + reimbursement +
                ", status=" + status +
                ", startDate=" + startDate +
                '}';
    }
}
