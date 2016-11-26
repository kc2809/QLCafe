package com.example.administrator.qlcafe;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.qlcafe.adapter.TableAdapter;
import com.example.administrator.qlcafe.database.MyDatabase;
import com.example.administrator.qlcafe.model.Food;
import com.example.administrator.qlcafe.model.Table;
import com.example.administrator.qlcafe.process.data.ProcessData;
import com.example.administrator.qlcafe.process.data.RefreshService;

import org.w3c.dom.Document;

import java.util.ArrayList;


public class ListTableActivity extends Activity implements Constant {

    GridView girdView;
    ArrayList<Table> arrTable ;
    TableAdapter adapter;

    //------------------
    ImageView imgLogout,imgRefresh;

    public static ArrayList<Food> menuFood=new ArrayList<>();

    MyDatabase database;

    BroadcastReceiver receiver;


    public static Food searchFood(int id){
        Food d=null ;
        for(int i=0;i<menuFood.size();++i){
            if(menuFood.get(i).getId_food() == id){
                return menuFood.get(i);
            }
        }
        return d;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_table);

        init();

        //example data food
//        initData();

        getControls();


        addListener();
 //       setDataFromDatabase();


       runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJSOn().execute(url_table_list+MainActivity.getKey());
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new MenuTask().execute(url_Menu_list+MainActivity.getKey());
            }
        });



        receiverFromService();

    }

    private void exampleData(){
        arrTable = new ArrayList<>();
        arrTable.add(new Table(1, "ban 1", 0));
        arrTable.add(new Table(5,"ban 5",0));
        arrTable.add(new Table(3,"ban 3",1));
        arrTable.add(new Table(8,"ban 8",0));
        arrTable.add(new Table(45,"ban 45",0));
        arrTable.add(new Table(9,"ban 9",1));
        arrTable.add(new Table(7,"ban 7",0));
        arrTable.add(new Table(10,"ban 10",1));
        arrTable.add(new Table(6,"ban 6",0));

    }

    private void init(){
        database = new MyDatabase(this);
        database.getDatabase();
        arrTable = new ArrayList<>();
    //    exampleData();
     //   database.initTable(arrTable);

    }
    private void initData(){
        // load menu list in here

        menuFood = new ArrayList<>();
        String url = "http://image.flaticon.com/teams/1-freepik.jpg";
        menuFood.add(new Food(11,"cafe",15000,url));
        menuFood.add(new Food(22,"ca phao",15000,url));
        menuFood.add(new Food(33, "cachua baba", 20000, url));
        menuFood.add(new Food(44,"foodball",20000,url));
        menuFood.add(new Food(55, "fo mai", 20000, url));
        menuFood.add(new Food(66, "pho bo", 20000, url));
        menuFood.add(new Food(77, "one", 20000, url));

    }




    private void setDataFromDatabase(){
        arrTable.clear();
        arrTable = database.loadTableDataFromTable_tbl();
        adapter = new TableAdapter(this,R.layout.item_table_layout,arrTable);
        adapter.notifyDataSetChanged();
        girdView.setAdapter(adapter);
    }


    private void addListener() {
        adapter = new TableAdapter(this,R.layout.item_table_layout,arrTable);


        girdView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(arrTable.get(i).getStatus() == 0){
                    database.freeTableById(arrTable.get(i).getId());
                }


         //       Toast.makeText(ListTableActivity.this,arrTable.get(i).toString(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ListTableActivity.this,OrderActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("BAN", arrTable.get(i));
                intent.putExtra("DATA", b);
                startActivityForResult(intent, Constant.OPEN_ORDER_ACTIVITY);
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

        (new LogoutTask()).execute(MainActivity.getUserName(), MainActivity.getKey());

    }

    private void refresh() {
        Toast.makeText(ListTableActivity.this,"Refresh",Toast.LENGTH_LONG).show();
        (new UpdateTableTask()).execute(Constant.url_table_list + MainActivity.getKey());
    }

    private void getControls() {
        girdView = (GridView)findViewById(R.id.gvListTable);
        imgLogout = (ImageView)findViewById(R.id.imgLogOut);
        imgRefresh = (ImageView)findViewById(R.id.imgRefresh);
    }


    class LogoutTask extends AsyncTask<String,Void, Void>{


        @Override
        protected Void doInBackground(String... strings) {
            String request = "username=" + strings[0]+
                    "&key=" +strings[1];
            String url = Constant.url_logout + request;

            String s =  ProcessData.getInstance().docNoiDung_Tu_URL(url);

            String result = ProcessData.getInstance().xmlParseLogin(ProcessData.getInstance().getDomElement(s));

            System.out.println("LOGOUT :"+result);
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            finish();

        }
    }


    //------------------------------ doc XML tu hue
    //--------- khoi tao
      class docJSOn extends AsyncTask<String,Integer,String> {
        protected String doInBackground(String... params){
            return  ProcessData.getInstance().docNoiDung_Tu_URL(params[0]);
        }

        protected void onPostExecute(String s){
            System.out.println("----k@@@ : "+ s);
            Document doc =  ProcessData.getInstance().getDomElement(s);
            ArrayList<Table> arr =  ProcessData.getInstance().xmlParse(doc);
            database.initTable(arr);
            setDataFromDatabase();
        }
    }

    //-------- update table
    class UpdateTableTask extends AsyncTask<String,Integer,String> {
        protected String doInBackground(String... params){
            return  ProcessData.getInstance().docNoiDung_Tu_URL(params[0]);
        }

        protected void onPostExecute(String s){
            System.out.println("----k@@@ : "+ s);
            Document doc =  ProcessData.getInstance().getDomElement(s);
            ArrayList<Table> arr =  ProcessData.getInstance().xmlParse(doc);
            database.updateAllStatusTable(arr);
            setDataFromDatabase();
        }
    }


    //-------------------


    //--------- read menu list
    class MenuTask extends AsyncTask<String,Void ,String>{

        @Override
        protected String doInBackground(String... strings) {
            return ProcessData.getInstance().docNoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("noi dung: "+s);
            Document doc = ProcessData.getInstance().getDomElement(s);
            menuFood = ProcessData.getInstance().xmlParseMenu(doc);
        }
    }
    //---------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("TAO NHAN DUOC BACK O NGOAI");
        refresh();
 }


    //----------------------------------------

    private void receiverFromService(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("update.ui");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //refresh ui
                System.out.println("@@@@@@@ HAHAHAHAHA @@@@@@@");
           //     setDataFromDatabase();
                refresh();
            }
        };
        registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopService(new Intent(this, RefreshService.class));
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, RefreshService.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(new Intent(this, RefreshService.class));
    }

}
