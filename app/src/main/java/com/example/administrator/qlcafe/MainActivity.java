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

           String s =  ProcessData.getInstance().docNoiDung_Tu_URL("http://192.135.1.100:8080/ManagerCoffee/rest/serverApp/login?username=a2&password=1");

            System.out.println("AAA: "+ s);
            int a = ProcessData.getInstance().xmlParseLogin(ProcessData.getInstance().getDomElement(s));

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
