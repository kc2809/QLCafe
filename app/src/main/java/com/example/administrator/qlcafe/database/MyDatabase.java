package com.example.administrator.qlcafe.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.qlcafe.model.Order;

import java.util.ArrayList;


public class MyDatabase {
    static final String databaseName = "cafedata.db";
    SQLiteDatabase database=null;
    Context context;
    public MyDatabase(Context context){
        this.context = context;
    }


    public SQLiteDatabase getDatabase(){
        database = context.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE,null);

        //if database exist
        if(database!=null) {
            //if table time exist
            if (isTableExist(database, "table_tbl")) {
                return database;
            } else {
                createTableTbl();
            }

        }
        return database;
    }

    public boolean isTableExist(SQLiteDatabase database,String tableName){
        Cursor cursor = database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName
                + "'",null);
        if(cursor!=null){
            if(cursor.getCount() >0){
                cursor.close();
                return true;
            }
        }
        cursor.close();
        return false;
    }

    //----------- CREATE TABLE
    public void createTableTbl(){
        String sql = "CREATE TABLE [table_tbl] (\n" +
                "[Id] INTEGER  NOT NULL PRIMARY KEY ,\n" +
                "[idMon] TEXT  NULL,\n" +
                "[SoLuong] TEXT  NULL,\n" +
                "[TrangThai] TEXT  NULL\n" +
                ")";
        database.execSQL(sql);
    }

    /*---------------INSERT DATA-----------------
     *------------------------------------------
    */

    public boolean insertOrder(int idBan,int idMon,int soLuong){
        System.out.println("IDBAN " + idBan);
        Order order = loadDataTable_tblById(idBan);
        order.checkValidateOrder(idMon, soLuong);

        updateTableById(idBan, order);
        return true;
    }

    public boolean updateOrder(int idBan,int idMon,int soLuong){
        Order order = loadDataTable_tblById(idBan);
        order.checkValidateOrder(idMon, soLuong);

        updateTableById(idBan, order);
        return true;
    }


    public void comfirmOrder(int idBan){
        Order order = loadDataTable_tblById(idBan);
        order.comfirmOrder();

        updateTableById(idBan, order);
    }

    public boolean insertTable_tbl(int id,String dsIdMon,String dsSoLuong,String dsTrangThai){
        ContentValues content = new ContentValues();
        content.put("Id",id);
        content.put("idMon",dsIdMon);
        content.put("SoLuong",dsSoLuong);
        content.put("TrangThai",dsTrangThai);

        if(database.insert("table_tbl",null,content)==-1){
            //ERROR
            return false;
        }
        //SUCCESS
        return true;
     }

    //------------ init
    public void initTable(int n){
        deleteAllTable_tbl();
        String s="";
        for(int i=1;i<=n;++i){
            insertTable_tbl(i,s,s,s);
        }
    }


    /*---------------LOAD DATA-----------------
  *------------------------------------------
 */
    public ArrayList<Order> loadDataTable_tbl(){
        ArrayList<Order> arr = new ArrayList<>();
        Cursor cursor;
        cursor = database.query("table_tbl",null,null,null,null,null,null);
        int count = cursor.getCount();
        if(count!=0){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Order item = new Order(cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
                arr.add(item);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arr;
    }
    public Order loadDataTable_tblById(int id){
        Order item=new Order();
        Cursor cursor;
        cursor = database.query("table_tbl",null,"Id="+ id,null,null,null,null);
        int count = cursor.getCount();
        System.out.println("@@@@@@@@ count = "+count);
        if(count!=0) {
            cursor.moveToFirst();
            if(cursor.getString(1).equals("")){
                System.out.println("CHUA CO CON ME J CA ");
                return item;
            }
            System.out.println("dddd------------" + cursor.getString(1) + " - " + cursor.getString(2) + " - " + cursor.getString(3));
            item.setIdBan(cursor.getInt(1));
            if(!cursor.getString(1).equals("")) {
                item.setDsOrderByString(cursor.getString(1), cursor.getString(2), cursor.getString(3));
            }
        }
        cursor.close();
        return item;
    }

    public boolean freeTableById(int id){
        String s ="";
        ContentValues values = new ContentValues();
        values.put("idMon",s);
        values.put("SoLuong",s);
        values.put("TrangThai",s);

        int ret = database.update("table_tbl", values, "Id=" + id, null);
        if(ret ==0 ){
            //failed
            return false;
        }
        return true;
    }

    //UPDATE ----------
    public boolean updateTableById(int id,Order order){
        ContentValues values = new ContentValues();
        values.put("idMon", order.getStringDSMon());
        values.put("SoLuong",order.getStringDSSoLuong());
        values.put("TrangThai",order.getStringDSTrangThai());

        int ret = database.update("table_tbl", values, "Id=" + id, null);
        if(ret ==0 ){
            //failed
            return false;
        }
        return true;
    }

    public void deleteAllTable_tbl(){
        database.execSQL("delete from table_tbl");
    }

    public void deleteAllTable_MessageData(){
        database.execSQL("delete from tbl_message_data");
        database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE name='tbl_message_data'");
    }

}
