package com.example.administrator.qlcafe.model;

import java.io.Serializable;

/**
 * Created by Administrator on 10/20/2016.
 */
public class Food implements Serializable{
    int id_food;
    String name;
    int price;
    String img_url;

    public Food(int id_food, String name) {
        this.id_food = id_food;
        this.name = name;
    }



    public Food(int id_food, String name, int price) {
        this.id_food = id_food;
        this.name = name;
        this.price = price;
    }

    public Food(int id_food, String name, int price, String img_url) {
        this.id_food = id_food;
        this.name = name;
        this.price = price;
        this.img_url = img_url;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
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
