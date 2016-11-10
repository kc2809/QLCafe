package com.example.administrator.qlcafe.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 11/10/2016.
 */
public class BillDetail {
    ArrayList<ItemOrder> dsOrder;
    long sum;

    public BillDetail() {
    }

    public BillDetail(long sum, ArrayList<ItemOrder> dsOrder) {
        this.sum = sum;
        this.dsOrder = dsOrder;
    }

    public ArrayList<ItemOrder> getDsOrder() {
        return dsOrder;
    }

    public void setDsOrder(ArrayList<ItemOrder> dsOrder) {
        this.dsOrder = dsOrder;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }
}
