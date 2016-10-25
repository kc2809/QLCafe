package com.example.administrator.qlcafe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.qlcafe.R;
import com.example.administrator.qlcafe.model.Food;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 10/24/2016.
 */
public class ProductValueAdapter extends BaseAdapter implements Filterable {
    ArrayList<Food> arrFoodList;
    ArrayList<Food> arrFoodFilterList;
    LayoutInflater inflater;
    ValueFilter valueFiter;
    Context context;

    public ProductValueAdapter(ArrayList<Food> arrFoodList,Context context) {
        this.arrFoodList = arrFoodList;
        this.arrFoodFilterList = arrFoodList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        getFilter();
    }

    public ArrayList<Food> getArrFoodList() {
        return arrFoodList;
    }

    public void setArrFoodList(ArrayList<Food> arrFoodList) {
        this.arrFoodList = arrFoodList;
    }

    @Override
    public int getCount() {
        return arrFoodList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrFoodList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String url = "http://www.projectiradio.com/wp-content/uploads/2015/03/DMC4_Special_Edition_-_Nero.png";
        Holder viewHolder;
        if(convertView == null){
            viewHolder = new Holder();
            convertView = inflater.inflate(R.layout.product_list_layout,null);
            viewHolder.tvName = (TextView)convertView.findViewById(R.id.tvNameProduct);
            viewHolder.tvPrice = (TextView)convertView.findViewById(R.id.tvPrice);
            viewHolder.imgIcon = (ImageView)convertView.findViewById(R.id.iconFood);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (Holder)convertView.getTag();
        }
            viewHolder.tvName.setText(arrFoodList.get(position).getName());
            viewHolder.tvPrice.setText(String.format("%,d", arrFoodList.get(position).getPrice()));
            //
        //viewHolder.img....
         Picasso.with(context).load(arrFoodList.get(position).getImg_url()).into(viewHolder.imgIcon);


        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(valueFiter == null){
            valueFiter = new ValueFilter();
        }
        return valueFiter;
    }

    private class Holder{
        TextView tvName;
        TextView tvPrice;
        ImageView imgIcon;
    }

    private class ValueFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if(charSequence!=null && charSequence.length()>0){
                ArrayList<Food> filterList = new ArrayList<>();
                for(int i=0;i<arrFoodFilterList.size();++i){
                    if(arrFoodFilterList.get(i).getName().contains(charSequence)){
                        filterList.add(arrFoodFilterList.get(i));
                    }
                 }
                results.count= filterList.size();
                results.values = filterList;
            }
            else{
                results.count = arrFoodFilterList.size();
                results.values = arrFoodFilterList;
            }
            return results;
        }
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            arrFoodList = (ArrayList<Food>)filterResults.values;
            notifyDataSetChanged();
        }
    }



}
