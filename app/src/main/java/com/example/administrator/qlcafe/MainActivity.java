package com.example.administrator.qlcafe;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getControls();
        addListener();

        (new PostTask()).execute(Constant.url_login, "a1", "1");
    //    postData();
    }


    private void addListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check valid account

                //then start list table activity
                Intent intent = new Intent(MainActivity.this,ListTableActivity.class);
                startActivity(intent);

            }
        });
    }

    private void getControls() {
        edtUsername = (EditText)findViewById(R.id.edtUsername);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
    }

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


    public void postData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Constant.url_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("AAA: "+ response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters = new HashMap<String,String>();
                parameters.put("username","a2");
                parameters.put("password","1");

                return parameters;
            }
        };
        queue.add(request);
    }




}
