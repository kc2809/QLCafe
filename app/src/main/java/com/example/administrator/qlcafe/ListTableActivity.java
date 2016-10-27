package com.example.administrator.qlcafe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.qlcafe.adapter.TableAdapter;
import com.example.administrator.qlcafe.database.MyDatabase;
import com.example.administrator.qlcafe.model.Food;
import com.example.administrator.qlcafe.model.Table;

import org.json.JSONArray;
import org.json.JSONObject;
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


public class ListTableActivity extends ActionBarActivity {

    GridView girdView;
    ArrayList<Table> arrTable ;
    TableAdapter adapter;

    ImageView imgLogout,imgRefresh;

    public static ArrayList<Food> menuFood;

    MyDatabase database;


//    public String  url_table_status = "http://desktop-t6c29d8:8080/ManagerCoffee/rest/serverTable/list";
    public String  url_table_status = "http://192.168.137.1:8080/ManagerCoffee/rest/serverTable/list";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_table);




        init();
        initData();

        getControls();
        addListener();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new docJSOn().execute(url_table_status);
            }
        });

    }

    private void init(){
        database = new MyDatabase(this);
        database.getDatabase();


    }
    private void initData(){
        menuFood = new ArrayList<>();
        String url = "http://image.flaticon.com/teams/1-freepik.jpg";
        menuFood.add(new Food(11,"cafe",15000,url));
        menuFood.add(new Food(22,"ca phao",15000,url));
        menuFood.add(new Food(33,"cachua baba",20000,url));
        menuFood.add(new Food(44,"foodball",20000,url));
        menuFood.add(new Food(55,"fo mai",20000,url));
        menuFood.add(new Food(66, "pho bo", 20000, url));
        menuFood.add(new Food(77, "one", 20000, url));
    }




    private void setDataFromDatabase(){
        arrTable = database.loadDataStatus_tbl();
        adapter = new TableAdapter(this,R.layout.item_table_layout,arrTable);
        adapter.notifyDataSetChanged();
        girdView.setAdapter(adapter);
    }


    private void addListener() {
//        adapter = new TableAdapter(this,R.layout.item_table_layout,arrTable);


        girdView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ListTableActivity.this,arrTable.get(i).toString(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ListTableActivity.this,OrderActivity.class);
                Bundle b = new Bundle();
                b.putInt("IDBAN", arrTable.get(i).getId());
                intent.putExtra("DATA", b);
                startActivity(intent);
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


    //------ doc json tu hue
    private static String docNoiDung_Tu_URL(String theUrl)
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

    //---------
    class docJSOn extends AsyncTask<String,Integer,String> {
        protected String doInBackground(String... params){
            return docNoiDung_Tu_URL(params[0]);

        }

        protected void onPostExecute(String s){
            Toast.makeText(ListTableActivity.this,s,Toast.LENGTH_LONG).show();
            System.out.println("----k@@@ : "+ s);
            Document doc = getDomElement(s);
            xmlParse(doc);
        //    xulyJson(url_table_status);
            setDataFromDatabase();
        }
    }

    public ArrayList<Table> xulyJson(String s1){
      String s = "[{\"id_coffee_shop\":\"1\",\"id_table\":\"1\",\"name_table\":\"ban9\",\"status\":\"0\"},{\"id_coffee_shop\":\"1\",\"id_table\":\"3\",\"name_table\":\"ban3\",\"status\":\"1\"}]";


        ArrayList<Table> myArr = new ArrayList<>();

        try{
            System.out.println("HERE1 " );
            JSONArray jsonArray = new JSONArray(s);

        //    JSONArray
            System.out.println("HERE" );
            for(int i=0;i<jsonArray.length();++i){
       //         JSONObject object = (JSONObject) jsonArray.get(i);
          //      Table item = new Table(object.getInt("id_table"),object.getString("name_table"),object.getInt("status"));
        //        System.out.println("item : " + item.toString());
        //        myArr.add(item);
            }

            database.initStatusTable(myArr);
            database.initTable(myArr);

        }
        catch(Exception e){
            e.printStackTrace();
        }

        return myArr;
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

    public ArrayList<Table> xmlParse(Document doc){
        System.out.println("@@@@PARSE");
        ArrayList<Table> tables = new ArrayList<>();
        Element root = doc.getDocumentElement();
        NodeList list = root.getChildNodes();

        System.out.println("LENGTH = "+list.getLength());
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
        database.initStatusTable(tables);

        return tables;
    }
}
