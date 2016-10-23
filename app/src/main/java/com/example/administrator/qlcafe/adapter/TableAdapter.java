package com.example.administrator.qlcafe.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.qlcafe.R;
import com.example.administrator.qlcafe.model.Table;

import java.util.ArrayList;

/**
 * Created by Administrator on 10/18/2016.
 */
public class TableAdapter extends ArrayAdapter<Table>{
    int layoutId;
    ArrayList<Table> myArr;
    Activity context;

    public TableAdapter(Activity context, int resource,ArrayList<Table> objects) {
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
            final Table item = myArr.get(position);
            final TextView tv = (TextView)convertView.findViewById(R.id.tvLabel);
            final ImageView imageView = (ImageView)convertView.findViewById(R.id.imgTable);

            if(item.getStatus() == 1){
                imageView.setBackgroundColor(Color.parseColor("#ffff2624"));
            }
            else{

                imageView.setBackgroundColor(Color.parseColor("#b71faeff"));
            //    imageView.setBackgroundResource(0);
            }

            tv.setText(item.getLabel());
        }

        return convertView;
    }
}
