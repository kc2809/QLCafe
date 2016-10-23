package com.example.administrator.qlcafe.model;

/**
 * Created by Administrator on 10/20/2016.
 */
public class Food {
    int id_food;
    String name;

    public Food(int id_food, String name) {
        this.id_food = id_food;
        this.name = name;
    }

    public int getId_food() {
        return id_food;
    }

    public void setId_food(int id_food) {
        this.id_food = id_food;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
