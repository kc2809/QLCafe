package com.example.administrator.qlcafe.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 10/20/2016.
 */
public class Order {
    Table table;


    ArrayList<ItemOrder> dsOrder= new ArrayList<>();


    public Order() {
        dsOrder= new ArrayList<>();
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void comfirmOrder(){
        for(int i =0;i<dsOrder.size();i++){
            dsOrder.get(i).setTrangThai(1);
        }

        //gop trang thai
        if(dsOrder.size()>=2){
            int i =0;
            while(i<dsOrder.size()-1){
                for(int j=i+1;j<dsOrder.size();++j){
                    if(dsOrder.get(i).getIdMon() == dsOrder.get(j).getIdMon()){
                        System.out.println("BANG NHAU"+ dsOrder.size());
                        dsOrder.get(i).setSoLuong(dsOrder.get(i).getSoLuong()+ dsOrder.get(j).getSoLuong());
                        dsOrder.remove(j);
                    }
                }
                i++;
            }
        }

    }

    public void checkValidateOrder(int idMon,int soLuong){
        int check = 0;
        int k=0;
        for(int i=0;i<dsOrder.size();++i){
            if(dsOrder.get(i).getIdMon() == idMon){
                check = 1;
                k=i;
            }
        }
        //neu da~ co mon an
        if(check == 1){
            //neu mon an da duoc order roi
            if(dsOrder.get(k).getTrangThai()==1){
               check =2;
            }
        }

        //neu chua co mon an hoac da duoc order roi
        if(check == 0 || check ==2){
            //add to list
            ItemOrder item = new ItemOrder(idMon,soLuong,0);
            dsOrder.add(item);
        }
        //neu co mon an va chua order
        if(check == 1){
            dsOrder.get(k).setSoLuong(dsOrder.get(k).getSoLuong() + soLuong);
        }
    }


    public void updateQuantity(int idMon,int SoLuong){
        for(int i=0;i<dsOrder.size();++i){
            if(dsOrder.get(i).getIdMon() == idMon){

            }
        }
    }

//    public Order(int idBan,String dsMon,String dsSL,String dsTT){
//        this.idBan = idBan;
//       setDsOrderByString(dsMon,dsSL,dsTT);
//    }

    //get data from database
    public Order(int id,String name,int status,String dsMon,String dsSL,String dsTT){
        this.table = new Table(id,name,status);
        setDsOrderByString(dsMon,dsSL,dsTT);
    }

    public void setDsOrderByString(String dsMon,String dsSoLuong,String dsTT){
        String [] arrMon = dsMon.split("/");
        String [] arrSoLuong = dsSoLuong.split("/");
        String [] arrTT = dsTT.split("/");

        for(int i=0;i<arrMon.length;++i){
            ItemOrder item = new ItemOrder(Integer.parseInt(arrMon[i]),Integer.parseInt(arrSoLuong[i]),Integer.parseInt(arrTT[i]));
            dsOrder.add(item);
        }

    }

    public String getStringDSMon(){
        String result = "";
        for(int i=0;i<dsOrder.size();++i){
            result+= dsOrder.get(i).getIdMon()+"/";
        }

        return result;
    }

    public String getStringDSSoLuong(){
        String result = "";
        for(int i=0;i<dsOrder.size();++i){
            result+= dsOrder.get(i).getSoLuong()+"/";
        }

        return result;
    }
    public String getStringDSTrangThai(){
        String result = "";
        for(int i=0;i<dsOrder.size();++i){
            result+= dsOrder.get(i).getTrangThai()+"/";
        }

        return result;
    }

    public ArrayList<ItemOrder> getDsOrder() {
        return dsOrder;
    }

    public void setDsOrder(ArrayList<ItemOrder> dsOrder) {
        this.dsOrder = dsOrder;
    }




    @Override
    public String toString() {
        return "IDBAN : "+ table.getId() + "-";
    }
}
