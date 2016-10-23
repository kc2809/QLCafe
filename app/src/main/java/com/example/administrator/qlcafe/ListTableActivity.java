package com.example.administrator.qlcafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.qlcafe.adapter.TableAdapter;
import com.example.administrator.qlcafe.model.Table;

import java.util.ArrayList;


public class ListTableActivity extends ActionBarActivity {

    GridView girdView;
    ArrayList<Table> arrTable;
    TableAdapter adapter;

    ImageView imgLogout,imgRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_table);

        //example
        setData();

        getControls();
        addListener();
    }

    private void setData() {
        arrTable = new ArrayList<>();

        for(int i=0;i<8;i++){
            Table item = new Table(i,0);
            arrTable.add(item);
        }
        arrTable.get(5).setStatus(1);
    }

    private void addListener() {
        adapter = new TableAdapter(this,R.layout.item_table_layout,arrTable);
        girdView.setAdapter(adapter);

        girdView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ListTableActivity.this,arrTable.get(i).toString(),Toast.LENGTH_LONG).show();
                startActivity(new Intent(ListTableActivity.this,OrderActivity.class));
                finish();
            }
        });

        //-----------log out
        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });

        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });
    }

    private void logOut() {
        Toast.makeText(ListTableActivity.this,"LOG OUT",Toast.LENGTH_LONG).show();
        finish();
    }

    private void refresh() {
        Toast.makeText(ListTableActivity.this,"Refresh",Toast.LENGTH_LONG).show();
    }

    private void getControls() {
        girdView = (GridView)findViewById(R.id.gvListTable);
        imgLogout = (ImageView)findViewById(R.id.imgLogOut);
        imgRefresh = (ImageView)findViewById(R.id.imgRefresh);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_table, menu);
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
