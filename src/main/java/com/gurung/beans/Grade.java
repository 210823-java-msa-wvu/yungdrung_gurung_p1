package com.gurung.beans;

public class Grade {
    private Integer id;
    private String gradeLabel;

    public Grade() {
    }

    public Grade(Integer id, String gradeLabel) {
        this.id = id;
        this.gradeLabel = gradeLabel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGradeLabel() {
        return gradeLabel;
    }

    public void setGradeLabel(String gradeLabel) {
        this.gradeLabel = gradeLabel;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", gradeLabel='" + gradeLabel + '\'' +
                '}';
    }
}

