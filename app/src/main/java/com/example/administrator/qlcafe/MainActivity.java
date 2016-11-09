package com.example.administrator.qlcafe;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.qlcafe.process.data.ProcessData;

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
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity implements Constant{
    EditText edtUsername,edtPassword;
    Button btnLogin;

    public static String userName=null;
    public static String key=null;

    public static void setKey(String key2){
        key = key2;
    }
    public static String getKey(){
        return key;
    }

    public static void setUserName(String user){
        userName = user;
    }

    public static String getUserName(){
        return userName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getControls();
        addListener();

   //     (new PostTask()).execute(Constant.url_login, "a1", "1");
    //    postData();
    }


    private void addListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check valid account
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                setUserName(username);
                (new PostLoginTask()).execute(username, password);

            }
        });
    }

    private void startListTableActivity(){
        //then start list table activity
        Intent intent = new Intent(MainActivity.this,ListTableActivity.class);
        startActivity(intent);
    }

    private void getControls() {
        edtUsername = (EditText)findViewById(R.id.edtUsername);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
    }

    private class PostLoginTask extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... strings) {

            System.out.println("USERNAME + PASS: "+ strings[0]+ "-" +strings[1]);
            String request = "username=" + strings[0]+
                    "&password=" + strings[1];
            String url = Constant.url_login + request;

            String s =  ProcessData.getInstance().docNoiDung_Tu_URL(url);

            String result = ProcessData.getInstance().xmlParseLogin(ProcessData.getInstance().getDomElement(s));

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("false")){
                Toast.makeText(MainActivity.this,"Username or Password is wrong.\nTry Again",Toast.LENGTH_LONG).show();
            }
            else{
                setKey(s);
                Toast.makeText(MainActivity.this,"OK",Toast.LENGTH_LONG).show();
                startListTableActivity();
            }
        }
    }



    //--------------------
    private class PostTask extends AsyncTask<String, Integer, Void> {

        @Override
        protected Void doInBackground(String... params) {

           String s =  ProcessData.getInstance().docNoiDung_Tu_URL("http://192.135.1.100:8080/manage-coffee/rest/serverApp/login?username=kim&password=123");

            System.out.println("AAA: "+ s);
             ProcessData.getInstance().xmlParseLogin(ProcessData.getInstance().getDomElement(s));

            //---------------
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.135.1.100:8080/manage-coffee/rest/serverOrder/Menu");

            try {
                StringEntity se = new StringEntity( "<orders>\n" +
                        "     <order>\n" +
                        "       \t<id_menu>1</id_menu>\n" +
                        "        <count_menu>3</count_menu>\n" +
                        "     </order>\n" +
                        "     <orders>\n" +
                        "        <id_menu>1</id_menu>\n" +
                        "        <count_menu>3</count_menu>\n" +
                        "     </orders>\n" +
                        "    <idStaff>1</idStaff>\n" +
                        "    <idTable>3</idTable>\n" +
                        "    <key>07d90734dbc14ce783e0d118029b3394</key>\n" +
                        "</orders>\n", HTTP.UTF_8);
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

    //-----------


}
