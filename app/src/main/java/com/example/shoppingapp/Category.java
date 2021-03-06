package com.example.shoppingapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Category extends AppCompatActivity {
    //Muestra una lista de las categorías
    static String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Intent intent = getIntent();
        getResources().getString(R.string.ip);
        ip = getResources().getString(R.string.ip);
        //new findCategories(this).execute("http://ip:9000/Application/findCategorries");
        new findCategories(this).execute("http://"+ip+":9000/Application/findCategories");
    }

    //Buascar todas las categorias de los productos para luego mostrearlas en una listview
    private class findCategories extends AsyncTask<String,Void,String> {
        Context context;
        InputStream stream =null;
        String str="";
        String result=null;

        private findCategories(Context context){
            this.context=context;
        }
        @Override
        protected String doInBackground(String... urls) {
            try{
                String query= String.format(urls[0]);
                URL url= new URL(query);
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                stream=conn.getInputStream();
                BufferedReader reader =null;
                StringBuilder sb= new StringBuilder();

                reader= new BufferedReader(new InputStreamReader(stream));

                String line=null;
                while((line=reader.readLine())!= null){
                    sb.append(line);
                }

                result = sb.toString();

                Log.i("Categories Respones",result);

                return result;

            }
            catch (IOException e){
                return null;
            }
        }
        @Override
        protected void onPostExecute(String result){
            //Add item to adapter
            // Factory method to convert an array of JSON objects into a list of objects
            // User.fromJson(jsonArray);
            JSONArray jsonObjects = null;
            ArrayList<String> categories = new ArrayList<String>();
            try {
                jsonObjects = new JSONArray(result);
                for (int i = 0; i < jsonObjects.length(); i++) {
                    try {
                        categories.add((String)jsonObjects.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Construct the data source
            //Create the adapter to convert the array to views
            CategoryAdapter adapter = new CategoryAdapter(getApplicationContext(),categories);
            //Attach the adapter to a ListView
            final ListView listView =(ListView) findViewById(R.id.list_category);
            listView.setAdapter(adapter);
            //Al hacer click en un elemento de la lista se nos abre la lista de productos que pertenecen a la categoria
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent1= new Intent(context,Products.class);
                    String category =listView.getItemAtPosition(position).toString();
                    //Toast.makeText(context, listView.getItemAtPosition(position)+"", Toast.LENGTH_SHORT).show();
                    intent1.putExtra("category",category);
                    startActivity(intent1);
                }
            });

        }

    }



}
