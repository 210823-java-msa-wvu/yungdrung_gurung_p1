package com.gurung.beans;

import java.util.Date;

public class Attachment {
    private  Integer id;
    private  String documentName;
    private Date createdAt ;
    private Reimbursement reimbursement;
    private Employee employee;

    public Attachment() {
    }

    public Attachment(Integer id, String documentName, Date createdAt, Reimbursement reimbursement, Employee employee) {
        this.id = id;
        this.documentName = documentName;
        this.createdAt = createdAt;
        this.reimbursement = reimbursement;
        this.employee = employee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Reimbursement getReimbursement() {
        return reimbursement;
    }

    public void setReimbursement(Reimbursement reimbursement) {
        this.reimbursement = reimbursement;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", documentName='" + documentName + '\'' +
                ", createdAt=" + createdAt +
                ", reimbursement=" + reimbursement +
                ", employee=" + employee +
                '}';
    }
}
