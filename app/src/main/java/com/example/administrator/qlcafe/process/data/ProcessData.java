package com.example.administrator.qlcafe.process.data;

import android.util.Log;

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
    //------------ read xml menu list
    public int xmlParseLogin(Document doc){
        int result = -1;

        Element root = doc.getDocumentElement();
        System.out.println("ROOT : " +root.getNodeName());
        NodeList list = root.getChildNodes();
        Node n = list.item(0);
        System.out.println("ITEM : " + n.getNodeName());
        Element e = (Element) n;
        System.out.println("re : " + e.getTextContent());


        result = Integer.parseInt(e.getTextContent());

        System.out.println("REUSLT : "+ result);
        return result;
    }
}
