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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.qlcafe.Constant;
import com.example.administrator.qlcafe.ModifyQuantityActivity;
import com.example.administrator.qlcafe.OrderActivity;
import com.example.administrator.qlcafe.R;
import com.example.administrator.qlcafe.adapter.OrderItemAdapter;
import com.example.administrator.qlcafe.database.MyDatabase;
import com.example.administrator.qlcafe.model.ItemOrder;
import com.example.administrator.qlcafe.model.Order;

/**
 * Created by Administrator on 10/19/2016.
 */
public class MyOrderFragment extends Fragment implements Constant {
    Button btnCofirm,btnClear;
    TextView tvTotalPrice;
    ListView lvDetail;
 //   ArrayList<ItemOrder> arrOder;
    OrderItemAdapter adapter;
    Order order;
    int idBan;
    MyDatabase database;

    ItemOrder selectedItem;
    int position = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.my_order_layout,container,false);
        lvDetail = (ListView)v.findViewById(R.id.lvDetailOrder);
        tvTotalPrice = (TextView)v.findViewById(R.id.tvTotal);
        btnCofirm = (Button)v.findViewById(R.id.btnConfirm);
        btnClear = (Button)v.findViewById(R.id.btnClearOrder);

        init();
        addData();

        addListener();
        lvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                selectedItem = order.getDsOrder().get(i);
                Intent intent = new Intent(getActivity(), ModifyQuantityActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("ITEMORDER",selectedItem);
                b.putInt("REQUEST", OPEN_MODIFY_ACTIVITY);
                b.putInt("QUANTITY",selectedItem.getSoLuong());
                intent.putExtra("DATA",b);
                startActivityForResult(intent, OPEN_MODIFY_ACTIVITY);
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

    private void init(){
        database = new MyDatabase(getActivity());
        database.getDatabase();
    }
    private void addData() {
        OrderActivity activity = (OrderActivity)getActivity();
        idBan = activity.getIdBan();
        refresh();
    }

    private void refresh(){

        order = database.loadDataTable_tblById(idBan);
        System.out.println("Tao refresh day "+ order.toString());
        adapter = new OrderItemAdapter(getActivity(),R.layout.item_order_layout,order.getDsOrder());
        lvDetail.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void addListener(){
        btnCofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.comfirmOrder(getIDBan());
                refresh();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private int getIDBan() {
        OrderActivity activity = (OrderActivity)getActivity();
        return  activity.getIdBan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == MODIFY_SUCCESS){
            Bundle b = data.getBundleExtra("DATA");
            int num = (int)b.getInt("QUANTITY");

            order.getDsOrder().get(position).setSoLuong(num);
            database.updateTableById(getIDBan(),order);
            refresh();
        }
    }
}
