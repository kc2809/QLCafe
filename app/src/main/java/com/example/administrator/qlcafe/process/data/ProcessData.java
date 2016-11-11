package com.example.administrator.qlcafe.process.data;

import android.util.Log;

import com.example.administrator.qlcafe.model.BillDetail;
import com.example.administrator.qlcafe.model.Food;
import com.example.administrator.qlcafe.model.ItemOrder;
import com.example.administrator.qlcafe.model.Table;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Administrator on 10/28/2016.
 */
public class ProcessData {
    private static ProcessData instance = null;

    private ProcessData(){}

    public static ProcessData getInstance(){
        if(instance ==null){
            instance = new ProcessData();
        }
        return instance;
    }

    public  String docNoiDung_Tu_URL(String theUrl)
    {
        StringBuilder content = new StringBuilder();

        try
        {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }

    // read xml parse doom
    public Document getDomElement(String xml){
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        // return DOM
        return doc;
    }


    //--------------------------- read table list xml
    public ArrayList<Table> xmlParse(Document doc){
        Element root = doc.getDocumentElement();
        NodeList list = root.getChildNodes();

        System.out.println("@@@@PARSE");
        ArrayList<Table> tables = new ArrayList<>();
        System.out.println("LENGTH = " + list.getLength());
        for(int i=0;i<list.getLength();++i){
            Node node = list.item(i);
            if(node instanceof  Element){
                Element table = (Element) node;
                NodeList listChild = table.getElementsByTagName("id_table");
                int id_Table = Integer.parseInt(listChild.item(0).getTextContent());
                listChild = table.getElementsByTagName("name_table");
                String name_Table = listChild.item(0).getTextContent();
                listChild = table.getElementsByTagName("status");
                int status_Table = Integer.parseInt(listChild.item(0).getTextContent());

                System.out.println(id_Table + " - " + name_Table + " - " + status_Table);
                Table item = new Table(id_Table,name_Table,status_Table);
                tables.add(item);
            }
        }
        return tables;
    }
    //------------ read xml login
    public String xmlParseLogin(Document doc){
        String result =null;
        try{
            Element root = doc.getDocumentElement();
            System.out.println("ROOT : " +root.getNodeName());
            NodeList list = root.getChildNodes();
            Node n = list.item(0);
            System.out.println("ITEM : " + n.getNodeName());
            Element e = (Element) n;
            System.out.println("re : " + e.getTextContent());


            result = e.getTextContent();

            System.out.println("REUSLT : "+ result);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    //------------ read xml menu list
    //--------------------------- read table list xml
    public ArrayList<Food> xmlParseMenu(Document doc){
        Element root = doc.getDocumentElement();
        NodeList list = root.getChildNodes();

        System.out.println("@@@@PARSE");
        ArrayList<Food> menu = new ArrayList<>();
    //    System.out.println("LENGTH = " + list.getLength());
        for(int i=0;i<list.getLength();++i){
            Node node = list.item(i);
            if(node instanceof  Element){
                Element table = (Element) node;
                NodeList listChild = table.getElementsByTagName("id_Menu");
                int id_Food = Integer.parseInt(listChild.item(0).getTextContent());
                listChild = table.getElementsByTagName("name");
                String name_Food = listChild.item(0).getTextContent();
                listChild = table.getElementsByTagName("link");
                String img_url = listChild.item(0).getTextContent();
                listChild = table.getElementsByTagName("price");
                float piceF =Float.parseFloat(listChild.item(0).getTextContent());
                int price = (int)piceF;
                System.out.println(id_Food + " - " + name_Food + " - " + img_url + " - "+ price);
                Food item = new Food(id_Food,name_Food,price,img_url);
                menu.add(item);
            }
        }
        return menu;
    }


    //-------- read xml detail
    public BillDetail xmlParseBillDetail(Document doc){
        BillDetail bill = new BillDetail();
        ArrayList<ItemOrder> dsOrder = new ArrayList<>();

        Element root = doc.getDocumentElement();
        NodeList list = root.getChildNodes();
        Node n = list.item(0);
        Element e = (Element)n;
        System.out.println("re : " + e.getNodeName());

        NodeList listChild = e.getChildNodes();

        //--- sum
        Node sum = listChild.item(listChild.getLength()-1);
        System.out.println("NODE NAMEEE: "+ sum.getNodeName());
        Element eSum = (Element) sum;

        float sumOrder = Float.parseFloat(eSum.getTextContent());
        System.out.println("SUM  : " + eSum.getNodeName()+ " - "+ eSum.getTextContent() + " - " + sumOrder);
        if(sumOrder == 0 ){
            System.out.println("deo co con me j ca");
            bill.setSum(0);
            return bill;
        }


        //get bill detail
        for(int i=0;i<listChild.getLength()-1;++i){
            Node node = listChild.item(i);
            Element element = (Element)node;
            System.out.println("NODE NAME : "+ node.getNodeName());

            NodeList listChildren = element.getElementsByTagName("id_menu");
            int id_Food = Integer.parseInt(listChildren.item(0).getTextContent());
            listChildren = element.getElementsByTagName("count_menu");
            int count =Integer.parseInt(listChildren.item(0).getTextContent());
            listChildren = element.getElementsByTagName("cost_menu");
            float cost =Float.parseFloat(listChildren.item(0).getTextContent());
            listChildren = element.getElementsByTagName("count_money");
            float sumElement = Float.parseFloat(listChildren.item(0).getTextContent());

            ItemOrder item = new ItemOrder(id_Food,count,0,(int)sumElement,(int)cost);
            dsOrder.add(item);
            System.out.println( id_Food + " - "+ count + " - "+ cost + " - "+ sumElement);
        }



        bill.setDsOrder(dsOrder);
        bill.setSum((long)sumOrder);

        return bill;
    }
    //--
}
