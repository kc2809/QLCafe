package com.example.administrator.qlcafe.model;

import android.os.AsyncTask;

import com.example.administrator.qlcafe.Constant;
import com.example.administrator.qlcafe.ListTableActivity;
import com.example.administrator.qlcafe.MainActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 10/20/2016.
 */
public class Order {
    Table table;


    ArrayList<ItemOrder> dsOrder= new ArrayList<>();


    public int tongtienOrder(){
        int result = 0;

        for(int i=0;i<dsOrder.size();++i){
            Food d  = ListTableActivity.searchFood(dsOrder.get(i).getIdMon());
            result += d.getPrice()*dsOrder.get(i).getSoLuong();
        }
        return result;
    }

    public Order() {
        dsOrder= new ArrayList<>();
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void removeById(int id){
        dsOrder.remove(id);
    }

    public void comfirmOrder(){
        //send order that has status is 0
        // sendOrder(dsOrder.get(i))
     //   System.out.println("danh sach ORDER: \n"+toString());
        ArrayList<ItemOrder> dsOrderServer = new ArrayList<>();
        for(int i =0;i<dsOrder.size();i++){
           if(dsOrder.get(i).getTrangThai()== 0){
               System.out.println(dsOrder.get(i).toString());
               dsOrderServer.add(dsOrder.get(i));
           }
        }

        if(dsOrderServer.size()>0){
            String xml = xmlString(dsOrderServer);
            System.out.println("XML: "+ xml);
            //post to server
            (new PostTask()).execute(Constant.url_Order,xml);
        }


        //
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


    public String xmlString(ArrayList<ItemOrder> listOrder){
        String xml="<orders>\n";

        for(int i=0;i<listOrder.size();++i){
            xml +="<orders>\n";
                xml += "<id_menu>" +
                        listOrder.get(i).getIdMon() +
                "</id_menu>\n";

                xml += "<count_menu>" +
                        listOrder.get(i).getSoLuong() +
                "</count_menu>\n";

            xml +="</orders>\n";
        }
        xml+=" <idTable>" + table.getId()+
                "</idTable>\n";
        xml+=" <key>" + MainActivity.getKey()+
                "</key>\n";
        xml +="</orders>\n";
        return xml;
    }

    @Override
    public String toString() {
        String result = "RESULTTTT: \n";
        result += "IDBAN : "+ table.getId() + "- \n";

        if(dsOrder.size()>0){
            for(int i=0;i<dsOrder.size();++i){
                result += dsOrder.get(i).toString();
                result+="\n";
            }
        }

        result +="END";
        return result;

    }

    private class PostTask extends AsyncTask<String, Integer, Void> {

        @Override
        protected Void doInBackground(String... params) {
            //---------------
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);

            try {
                StringEntity se = new StringEntity(params[1], HTTP.UTF_8);
                se.setContentType("application/xml");
                httppost.setEntity(se);

                HttpResponse httpresponse = httpclient.execute(httppost);
                HttpEntity resEntity = httpresponse.getEntity();
                String responseXml = EntityUtils.toString(httpresponse.getEntity());
                //     System.out.println("RESPONSE: " +EntityUtils.toString(resEntity));
                System.out.println("RESPONSE: " +responseXml);
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //-------------------

            return null;
        }
    }


}
