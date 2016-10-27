package com.example.administrator.qlcafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.qlcafe.database.MyDatabase;
import com.example.administrator.qlcafe.model.Food;
import com.example.administrator.qlcafe.model.ItemOrder;
import com.example.administrator.qlcafe.model.Table;


public class ModifyQuantityActivity extends ActionBarActivity implements Constant{
    Button btnOk,btnPlus,btnSubtrac;
    TextView tvNum;
    int num;
    int request;
    Food item;

    Table banSelected;
    ItemOrder itemOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_quantity);


        addControls();
        getData();
        addListener();
    }

    private void addListener() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = getIntent();
                Bundle b = new Bundle();
                if(request == OPEN_MODIFY_ACTIVITY_FOR_ADD){


                    if(num!=0){
                        //add to database if quantity over 0
                        MyDatabase database  = new MyDatabase(ModifyQuantityActivity.this);
                        database.getDatabase();
                        database.insertOrder(banSelected.getId(),item.getId_food(),num);
                        //---------------
                        Toast.makeText(getApplicationContext(), "Them thanh cong", Toast.LENGTH_LONG).show();
                        OrderActivity.isChanged = true;
                    }

                    setResult(MODIFY_SUCCESS, in);
                    finish();
                }
                else{
                    b.putInt("QUANTITY",num);
                    in.putExtra("DATA",b);
                    setResult(MODIFY_SUCCESS, in);
                    finish();
                }
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num++;
                tvNum.setText(num+"");
            }
        });
        btnSubtrac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num>0){
                    num--;
                }
                tvNum.setText(num + "");
            }
        });
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle b = intent.getBundleExtra("DATA");

        request = (int)b.getInt("REQUEST");

        if(request == OPEN_MODIFY_ACTIVITY_FOR_ADD){
            num = (int)b.getInt("QUANTITY");
            item = (Food)b.getSerializable("FOOD");
            banSelected = (Table)b.getSerializable("BAN");
            tvNum.setText(num + "");
         }

        if(request == OPEN_MODIFY_ACTIVITY){
            itemOrder = (ItemOrder)b.getSerializable("ITEMORDER");
            num= itemOrder.getSoLuong();
            tvNum.setText(num+"");
        }

    }

    private void addControls() {
        btnOk = (Button)findViewById(R.id.btnOK);
        btnPlus = (Button)findViewById(R.id.btnPlus);
        btnSubtrac = (Button)findViewById(R.id.btnSubtract);

        tvNum = (TextView)findViewById(R.id.tvNum);
    }





}
