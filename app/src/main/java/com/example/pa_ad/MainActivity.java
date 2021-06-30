package com.example.pa_ad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

 String URL = "https://backsmarthome.herokuapp.com/";
 RequestQueue requestQueue;
 EditText edittextuser;
 EditText edittexpassword;
 Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                edittextuser =  (EditText) findViewById(R.id.edittextuser);
                edittexpassword =  (EditText)findViewById(R.id.edittexpassword);
                String loginjson = "{\n" +
                        "   \"email\":\""+edittextuser.getText()+"\",\n" +
                        "   \"password\":\""+edittexpassword.getText()+"\"\n" +
                        "}";
                Log.d("JSONUSER",loginjson);
                POSTVolley(loginjson);
            }
        });

    }

    private void POSTVolley(String datajson){

        //Obtención de datos del web service utilzando Volley
        requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(
                Request.Method.POST,URL+"webresources/users/logIn",
                //DataStatic.gerUrlApi("persona/logIn"),
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      /*  MyLogs.info("ws todo bien");
                        //Procesar las respuesta y armar un Array con estos
                        MyLogs.detailedLog(response);
                       */
                        int size = response.length();
                        JSONObject json_transform = null;
                        try {
                            if (size > 0)
                            {
                                json_transform = new JSONObject(response);
                                if(json_transform.getString("flag").equals("true")){
                                    Intent intent = new Intent(MainActivity.this, principal_activity_main.class);
                                    Bundle b = new Bundle();
                                    b.putString("name",json_transform.getJSONObject("data").getString("name"));
                                    b.putString("last_name",json_transform.getJSONObject("data").getString("last_name"));
                                    b.putString("type",json_transform.getJSONObject("data").getString("type"));
                                    b.putString("imguser",json_transform.getJSONObject("data").getString("imguser"));
                                    intent.putExtras(b);
                                    startActivity(intent);
                                }
                                else{
                                    Log.d("Data","Credenciales Incorrectas");
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Log.d("Response",response);
                       // Intent intent = new Intent(MainActivity.this, principal_activity_main.class);

                       // Bundle b = new Bundle();
                       // b.putString("json",response);
                      //  intent.putExtras(b);
                        // Iniciamos la nueva actividad
                      //  startActivity(intent);

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return datajson == null ? "{}".getBytes("utf-8") : datajson.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {

                    return null;
                }
            }
        };
        requestQueue.add(request);
    }

}