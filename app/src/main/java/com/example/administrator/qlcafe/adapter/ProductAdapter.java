package com.example.administrator.qlcafe.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.qlcafe.R;
import com.example.administrator.qlcafe.model.Food;

import java.util.ArrayList;

/**
 * Created by Administrator on 10/20/2016.
 */
public class ProductAdapter extends ArrayAdapter<Food> {
    int layoutId;
    ArrayList<Food> myArr;
    Activity context;

    public ProductAdapter(Activity context, int resource,ArrayList<Food> objects) {
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
            Food item = myArr.get(position);

            TextView tvName = (TextView)convertView.findViewById(R.id.tvNamePro);
            ImageView img = (ImageView)convertView.findViewById(R.id.imgFood);

            if(item.getId_food() == 1){
                img.setImageResource(R.drawable.thuocla);
            }
            else if(item.getId_food() == 2){
                img.setImageResource(R.drawable.hambuger);
            }
            else{
                img.setImageResource(R.drawable.thuocla);
            }

            tvName.setText(item.getName());
        }

        return convertView;
    }
}
