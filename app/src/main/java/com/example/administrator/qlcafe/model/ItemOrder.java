package com.example.administrator.qlcafe.model;

import java.io.Serializable;

/**
 * Created by Administrator on 10/25/2016.
 */
public class ItemOrder implements Serializable{
    int idMon;
    int soLuong;
    int trangThai;
    int thanhtien;
    int dongia;

    public ItemOrder(int idMon, int soLuong, int trangThai) {
        this.idMon = idMon;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
    }

    public ItemOrder(int idMon, int soLuong, int trangThai, int thanhtien, int dongia) {
        this.idMon = idMon;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
        this.thanhtien = thanhtien;
        this.dongia = dongia;
    }

    public int getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(int thanhtien) {
        this.thanhtien = thanhtien;
    }

    public int getDongia() {
        return dongia;
    }

    public void setDongia(int dongia) {
        this.dongia = dongia;
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

    @Override
    public String toString() {
        return idMon + " - " + soLuong + " - " + trangThai;
    }
}
