package com.example.administrator.qlcafe.model;

/**
 * Created by Administrator on 10/20/2016.
 */
public class Order {
    int id;
    String nameProduct;
    int quantity;
    float price;
    int status;


    public Order() {
    }

    public Order(String nameProduct, int quantity, float price, int status) {
        this.nameProduct = nameProduct;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    public Order(int id, String nameProduct, int quantity, float price, int status) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
