package com.gurung.beans;

import java.util.Date;

public class Fund {
    private  Integer id;
    private Employee employee;
    private Float initialFund;
    private Float allocatedFund;
    private Float remainingFund;
    private boolean status;
    private Date createdAt;
    private Date updatedAt;
    private Date resetFundDate;
    private Reimbursement reimbursement;

    public Fund() {
    }

    public Fund(Integer id, Employee employee, Date resetFundDate) {
        this.id = id;
        this.employee = employee;
        this.resetFundDate = resetFundDate;
    }

    public Fund(Integer id, Employee employee, Float allocatedFund, Float remainingFund, boolean status, Date createdAt, Date updatedAt, Date resetFundDate, Reimbursement reimbursement) {
        this.id = id;
        this.employee = employee;
        this.allocatedFund = allocatedFund;
        this.remainingFund = remainingFund;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.resetFundDate = resetFundDate;
        this.reimbursement = reimbursement;
    }

    public Fund(Integer id, Employee employee, Float initialFund, Float allocatedFund, Float remainingFund, boolean status, Date createdAt, Date updatedAt, Date resetFundDate, Reimbursement reimbursement) {
        this.id = id;
        this.employee = employee;
        this.initialFund = initialFund;
        this.allocatedFund = allocatedFund;
        this.remainingFund = remainingFund;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.resetFundDate = resetFundDate;
        this.reimbursement = reimbursement;
    }

    public Reimbursement getReimbursement() {
        return reimbursement;
    }

    public void setReimbursement(Reimbursement reimbursement) {
        this.reimbursement = reimbursement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Float getInitialFund() {
        return initialFund;
    }

    public void setInitialFund(Float initialFund) {
        this.initialFund = initialFund;
    }

    public Float getAllocatedFund() {
        return allocatedFund;
    }

    public void setAllocatedFund(Float allocatedFund) {
        this.allocatedFund = allocatedFund;
    }

    public Float getRemainingFund() {
        return remainingFund;
    }

    public void setRemainingFund(Float remainingFund) {
        this.remainingFund = remainingFund;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public Date getResetFundDate() {
        return resetFundDate;
    }

    public void setResetFundDate(Date resetFundDate) {
        this.resetFundDate = resetFundDate;
    }

    @Override
    public String toString() {
        return "Fund{" +
                "id=" + id +
                ", employee=" + employee +
                ", initialFund=" + initialFund +
                ", allocatedFund=" + allocatedFund +
                ", remainingFund=" + remainingFund +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", resetFundDate=" + resetFundDate +
                ", reimbursement=" + reimbursement +
                '}';
    }
}
