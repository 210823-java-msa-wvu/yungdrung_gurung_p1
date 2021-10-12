package com.gurung.beans;

public class Designation {
    private Integer id;
    private String title;

    public Designation() {
    }

    public Designation(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Designation{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
