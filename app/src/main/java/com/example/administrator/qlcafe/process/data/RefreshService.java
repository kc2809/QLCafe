package com.example.administrator.qlcafe.process.data;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import com.example.administrator.qlcafe.Constant;
import com.example.administrator.qlcafe.database.MyDatabase;
import com.example.administrator.qlcafe.model.Table;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 10/28/2016.
 */
public class RefreshService extends Service implements Constant{
    Timer timer;
    TimerTask task;
    int timeSchedule= 30000;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        System.out.println("@@@@@@@@ START");
        super.onCreate();
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                (new docJSOn()).execute(Constant.url_table_list);
            }
        };

        timer.scheduleAtFixedRate(task,0,timeSchedule);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("@@@@@@@@ CANCEL");
        timer.cancel();
    }

    private void updateUI(){
        Intent local  = new Intent();
        local.setAction("update.ui");
        this.sendBroadcast(local);
    }

    class docJSOn extends AsyncTask<String,Integer,String> {
        MyDatabase database = new MyDatabase(getApplicationContext());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            database.getDatabase();
        }

        protected String doInBackground(String... params){
           return ProcessData.getInstance().docNoiDung_Tu_URL(params[0]);
         //   return docNoiDung_Tu_URL(params[0]);

        }

        protected void onPostExecute(String s){
            Document doc = ProcessData.getInstance().getDomElement(s);
            ArrayList<Table> tables = ProcessData.getInstance().xmlParse(doc);
            //    xulyJson(url_table_status);
            database.updateAllStatusTable(tables);
            System.out.println("KHANH DAI CA update");
            updateUI();
        }
    }

}
