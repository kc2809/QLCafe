package com.example.administrator.qlcafe.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.qlcafe.ListTableActivity;
import com.example.administrator.qlcafe.R;
import com.example.administrator.qlcafe.model.Food;
import com.example.administrator.qlcafe.model.ItemOrder;

import java.util.ArrayList;

/**
 * Created by Administrator on 10/20/2016.
 */
public class OrderItemAdapter extends ArrayAdapter<ItemOrder> {
    int layoutId;
    ArrayList<ItemOrder> myArr;
    Activity context;

    ArrayList<Food> menuFood = ListTableActivity.menuFood;

    public OrderItemAdapter(Activity context, int resource,ArrayList<ItemOrder> objects) {
        super(context, resource, objects);
        this.layoutId = resource;
        myArr = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater  = context.getLayoutInflater();
        convertView  = inflater.inflate(layoutId,null);
        if(myArr.size() > 0 && position >=0){
            final ItemOrder item = myArr.get(position);
            final TextView tvName = (TextView)convertView.findViewById(R.id.tvName);
            final TextView quantity = (TextView)convertView.findViewById(R.id.tvQuantity);
            final TextView totalPrice = (TextView)convertView.findViewById(R.id.tvTotalPrice);

            final ImageView imgStatus = (ImageView)convertView.findViewById(R.id.imgStatus);

            //received
            if(item.getTrangThai() == 0){
                imgStatus.setImageResource(R.drawable.received);
            }
            //served
            else{
                imgStatus.setImageResource(R.drawable.served);
            }

            Food d = searchInMenu(item.getIdMon());
            tvName.setText(d.getName());
            quantity.setText(item.getSoLuong()+"");
            totalPrice.setText(d.getPrice()* item.getSoLuong()+"");

        }

        return convertView;
    }

    public Food searchInMenu(int id){
        Food f= null;
        for(int i=0;i<menuFood.size();++i){
            if(id == menuFood.get(i).getId_food()){
                return menuFood.get(i);
            }
        }
        return f;
    }

}
