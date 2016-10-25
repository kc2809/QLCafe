package com.example.administrator.qlcafe.model;

import java.io.Serializable;

/**
 * Created by Administrator on 10/25/2016.
 */
public class ItemOrder implements Serializable{
    int idMon;
    int soLuong;
    int trangThai;

    public ItemOrder(int idMon, int soLuong, int trangThai) {
        this.idMon = idMon;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
    }

    public int getIdMon() {
        return idMon;
    }

    public void setIdMon(int idMon) {
        this.idMon = idMon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
