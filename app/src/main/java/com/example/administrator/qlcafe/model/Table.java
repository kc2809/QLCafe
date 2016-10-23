package com.example.administrator.qlcafe.model;

/**
 * Created by Administrator on 10/18/2016.
 */
public class Table {
    int id;
    String label;
    int status;

    public Table() {
    }

    public Table(int id, int status) {
        this.id = id;
        this.status = status;
        label = "Table " + id;
    }

    public Table(int id, String label, int status) {
        this.id = id;
        this.label = label;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return id + " - " + label + " status : " + status;
    }
}
