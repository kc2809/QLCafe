package com.example.administrator.qlcafe;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.qlcafe.adapter.ProductAdapter;
import com.example.administrator.qlcafe.model.Food;

import java.util.ArrayList;


public class ListProductActivity extends Activity {
    ListView lvProduct;
    ArrayList<Food> myArr;
    ProductAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        lvProduct = (ListView)findViewById(R.id.listView);

        addData();

        adapter = new ProductAdapter(this,R.layout.item_product_layout,myArr);

        lvProduct.setAdapter(adapter);

        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                finish();
            }
        });
    }

    private void addData() {
        myArr = new ArrayList<>();
        myArr.add(new Food(1,"Thuoc con ngua"));
        myArr.add(new Food(2,"Cocacola "));
        myArr.add(new Food(3,"Hambuger"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
