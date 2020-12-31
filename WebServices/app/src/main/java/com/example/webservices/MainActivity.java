package com.example.webservices;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private Button btnSubmit;
    String responseText;
    StringBuffer response;
    URL url;
    Activity activity;
    ArrayList<Users> users = new ArrayList();
    private ProgressDialog progressDialog;
    ListView listView;

    private String path= "https://raw.githubusercontent.com/LoyoSteve/APIREST/master/db.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        listView = (ListView)findViewById(R.id.lista);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                users.clear();
                new GetServerData().execute();
            }
        });

    }

    class GetServerData extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            return getWebServiceResponseData();
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Obteniendo datos de los usuarios");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            if(progressDialog.isShowing())
                progressDialog.dismiss();
            CustomUsers customUsers = new CustomUsers(activity, users);
            listView.setAdapter(customUsers);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(), "Seleccionaste al usuario: "+ users.get(position).getNombre(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        protected Void getWebServiceResponseData(){
            try{
                url = new URL(path);
                Log.d("server data", "Server data: "+path);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();

                Log.d("response", "Respuesta: "+responseCode);

                if(responseCode == HttpsURLConnection.HTTP_OK){
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    String output;
                    response = new StringBuffer();

                    while ((output =in.readLine())!=null){
                        response.append(output);
                    }
                    in.close();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

            responseText = response.toString();

            Log.d("respuesta", "data: "+responseText);
            try{
                JSONArray jsonarray = new JSONArray(responseText);
                for(int i = 0 ; i < jsonarray.length(); i++){
                    JSONObject jsonObject = jsonarray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String nombre = jsonObject.getString("nombre");
                    String apellido = jsonObject.getString("apellido");
                    int edad = jsonObject.getInt("edad");

                    Log.d("id", "id: "+id);
                    Log.d("nombre", "nombre: "+nombre);
                    Log.d("apellido", "apellido: "+apellido);
                    Log.d("edad", "edad: "+edad);
                    Users u = new Users(id, nombre, apellido, edad);
                    users.add(u);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
