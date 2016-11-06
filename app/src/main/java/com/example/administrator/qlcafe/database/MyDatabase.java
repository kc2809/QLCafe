package com.example.administrator.qlcafe.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.qlcafe.model.Order;
import com.example.administrator.qlcafe.model.Table;

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
                "[id] INTEGER  NOT NULL PRIMARY KEY ,\n" +
                "[name] TEXT NULL  ,\n" +
                "[status] INTEGER NULL  ,\n" +
                "[idmon] TEXT NULL,\n" +
                "[soluong] TEXT  NULL,\n" +
                "[dstrangthai] TEXT  NULL\n" +
                ")";
        database.execSQL(sql);
    }



    /*---------------INSERT DATA-----------------
     *------------------------------------------
    */

    public boolean insertOrder(int idBan,int idMon,int soLuong){

        Order order = loadOrderDataFromTable_tblById(idBan);
        System.out.println(order.toString());
        order.checkValidateOrder(idMon, soLuong);
        updateTableByOrder(order);
        System.out.println(order.toString());

        return true;
    }

    public boolean updateOrder(int idBan,int idMon,int soLuong){
        Order order = loadOrderDataFromTable_tblById(idBan);
        order.checkValidateOrder(idMon, soLuong);

     //   updateTableById(idBan, order);
        return true;
    }


    public void comfirmOrder(int idBan){
        Order order = loadOrderDataFromTable_tblById(idBan);
        order.comfirmOrder();

        updateTableByOrder(order);
    }

    //------------------------------------------ insert table
    public boolean insertTable_tbl(int id,String name,int status,String dsIdMon,String dsSoLuong,String dsTrangThai){
        ContentValues content = new ContentValues();
        content.put("id",id);
        content.put("name",name);
        content.put("status",status);
        content.put("idmon",dsIdMon);
        content.put("soluong",dsSoLuong);
        content.put("dstrangthai", dsTrangThai);

        if(database.insert("table_tbl",null,content)==-1){
            //ERROR
            return false;
        }
        System.out.println("INSERT THANH CONG");
        //SUCCESS
        return true;
     }

    //--------------------------------------------



    //----------------------------------------------------- Init
    public void initTable(ArrayList<Table> tables){
        deleteAllTable_tbl();
        String s="";
        for(int i=0;i<tables.size();++i) {
            insertTable_tbl(tables.get(i).getId(),tables.get(i).getLabel(),tables.get(i).getStatus(), s, s, s);
        }
    }



    /*---------------LOAD DATA-----------------
  *------------------------------------------
 */

    public ArrayList<Order> loadOrderDataFromTable_tbl(){
        ArrayList<Order> arr = new ArrayList<>();
        Cursor cursor;
        cursor = database.query("table_tbl",null,null,null,null,null,null);
        int count = cursor.getCount();
        if(count!=0){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Order item = new Order(cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getInt(cursor.getColumnIndex("status")),
                        cursor.getString(cursor.getColumnIndex("idmon")),
                        cursor.getString(cursor.getColumnIndex("soluong")),
                        cursor.getString(cursor.getColumnIndex("dstrangthai")));
                arr.add(item);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arr;
    }

    public ArrayList<Table> loadTableDataFromTable_tbl(){
        ArrayList<Table> arr = new ArrayList<>();
        Cursor cursor;
        cursor = database.query("table_tbl",null,null,null,null,null,null);
        int count = cursor.getCount();
        if(count!=0){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Table item = new Table(cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getInt(cursor.getColumnIndex("status")));

                arr.add(item);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arr;
    }




    public Order loadOrderDataFromTable_tblById(int id){
        Order item=new Order();
        Cursor cursor;
        cursor = database.query("table_tbl",null,"id="+ id,null,null,null,null);
        int count = cursor.getCount();
        if(count!=0) {
            cursor.moveToFirst();
            item.setTable(new Table(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getInt(cursor.getColumnIndex("status"))));

            if(cursor.getString(cursor.getColumnIndex("idmon")).equals("")){
                System.out.println("CHUA CO CON ME J CA ");
                return item;
            }


            if(!cursor.getString(cursor.getColumnIndex("idmon")).equals("")) {
                item.setDsOrderByString( cursor.getString(cursor.getColumnIndex("idmon")),
                        cursor.getString(cursor.getColumnIndex("soluong")),
                        cursor.getString(cursor.getColumnIndex("dstrangthai")));
            }
        }
        System.out.println("IN DATABASE : "+ item.toString());
        cursor.close();
        return item;
    }

    //--------------------------------------------------------
    public boolean updateStatusTable(Table table){
        ContentValues values = new ContentValues();
        values.put("id", table.getId());
        values.put("status", table.getStatus());

        int ret = database.update("table_tbl", values, "id=" + table.getId(), null);
        if(ret ==0 ){
            //failed
            return false;
        }
        return true;
    }

    public void updateAllStatusTable(ArrayList<Table> arr){
        for(int i=0;i<arr.size();++i){
            updateStatusTable(arr.get(i));
        }
    }

    //--------------------------------------------------------

    //---------------------------------------------------------------------
    public boolean freeTableById(int id){
        String s ="";
        ContentValues values = new ContentValues();
        values.put("idmon",s);
        values.put("soluong",s);
        values.put("dstrangthai", s);
        values.put("status",0);

        int ret = database.update("table_tbl", values, "id=" + id, null);
        if(ret ==0 ){
            //failed
            return false;
        }
        return true;
    }

    //UPDATE ----------
    public boolean updateTableByOrder(Order order){
        ContentValues values = new ContentValues();
        values.put("idmon", order.getStringDSMon());
        values.put("soluong",order.getStringDSSoLuong());
        values.put("dstrangthai",order.getStringDSTrangThai());

        int ret = database.update("table_tbl", values, "id=" + order.getTable().getId(), null);
        if(ret ==0 ){
            //failed
            return false;
        }
        return true;
    }


    public void deleteAllTable_tbl(){
        database.execSQL("delete from table_tbl");
    }



}
