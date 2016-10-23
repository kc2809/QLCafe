package com.example.administrator.qlcafe.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.qlcafe.R;
import com.example.administrator.qlcafe.model.Order;

import java.util.ArrayList;

/**
 * Created by Administrator on 10/20/2016.
 */
public class OrderItemAdapter extends ArrayAdapter<Order> {
    int layoutId;
    ArrayList<Order> myArr;
    Activity context;


    public OrderItemAdapter(Activity context, int resource,ArrayList<Order> objects) {
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
            final Order item = myArr.get(position);
            final TextView tvName = (TextView)convertView.findViewById(R.id.tvName);
            final TextView quantity = (TextView)convertView.findViewById(R.id.tvQuantity);
            final TextView totalPrice = (TextView)convertView.findViewById(R.id.tvTotalPrice);

            final ImageView imgStatus = (ImageView)convertView.findViewById(R.id.imgStatus);

            //received
            if(item.getStatus() == 0){
                imgStatus.setImageResource(R.drawable.received);
            }
            //preparing
            else if(item.getStatus() == 1){
                imgStatus.setImageResource(R.drawable.preparing);
            }
            //prepared
            else if(item.getStatus() == 2){
                imgStatus.setImageResource(R.drawable.prepared);
            }
            //served
            else{
                imgStatus.setImageResource(R.drawable.served);
            }

            tvName.setText(item.getNameProduct());
            quantity.setText(item.getQuantity()+"");
            totalPrice.setText(item.getPrice()+"");

        }

        return convertView;
    }

}
