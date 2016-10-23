package com.example.administrator.qlcafe.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.qlcafe.ModifyQuantityActivity;
import com.example.administrator.qlcafe.R;
import com.example.administrator.qlcafe.adapter.OrderItemAdapter;
import com.example.administrator.qlcafe.model.Order;

import java.util.ArrayList;

/**
 * Created by Administrator on 10/19/2016.
 */
public class MyOrderFragment extends Fragment {

    TextView tvTotalPrice;
    ListView lvDetail;
    ArrayList<Order> arrOder;
    OrderItemAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.my_order_layout,container,false);
        lvDetail = (ListView)v.findViewById(R.id.lvDetailOrder);
        tvTotalPrice = (TextView)v.findViewById(R.id.tvTotal);

        addData();
        adapter = new OrderItemAdapter(getActivity(),R.layout.item_order_layout,arrOder);
        lvDetail.setAdapter(adapter);

        lvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivityForResult(new Intent(getActivity(), ModifyQuantityActivity.class), 1900);
            }
        });

        lvDetail.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure to delete this order... ? ")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                return false;
            }
        });


        return v;
    }

    private void addData() {
        arrOder = new ArrayList<>();
        arrOder.add(new Order("Sting",2,8000,0));
        arrOder.add(new Order("Thuoc con ngua",1,5000,0));
        arrOder.add(new Order("Coca",3,6000,2));
        arrOder.add(new Order("Bat xiu",2,15000,1));
    }


}
