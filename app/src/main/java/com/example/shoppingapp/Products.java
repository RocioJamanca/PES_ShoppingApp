package com.example.shoppingapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Products extends AppCompatActivity {
    static String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Intent intent = getIntent();
        Intent intent1 = getIntent();
        String brand = intent.getStringExtra("brand");
        String category=intent1.getStringExtra("category");
        //Toast.makeText(this,brand,Toast.LENGTH_LONG).show();
        //Toast.makeText(this,category,Toast.LENGTH_LONG).show();
        ip=getResources().getString(R.string.ip);
        //new findCategories(this).execute("http://ip:9000/Application/findCategorries");
        if(brand!=null) {
            new Products.findByCategoryOrBrand(this).execute("http://" + ip + ":9000/Application/findByBrand?brand="+brand);
        }
        else if (category!=null)
        {
            new Products.findByCategoryOrBrand(this).execute("http://" + ip + ":9000/Application/findByCategory?category="+category);
        }
    }

    private class findByCategoryOrBrand extends AsyncTask<String,Void,String> {

        Context context;
        InputStream stream =null;
        String str="";
        String result=null;

        private findByCategoryOrBrand(Context context){
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

                Log.i("CategoryOrBrandRespo",result);

                return result;

            }
            catch (IOException e){
                return null;
            }
        }
        @Override
        protected void onPostExecute(String result){
            // TextView textView=(TextView) findViewById( R.id.textView6);
            //textView.setText(result);

            //Add item to adapter
            // Factory method to convert an array of JSON objects into a list of objects
            // User.fromJson(jsonArray);
            JSONArray jsonObjects = null;
            ArrayList<String> products = new ArrayList<String>();
            try {
                jsonObjects = new JSONArray(result);
                for (int i = 0; i < jsonObjects.length(); i++) {
                    try {
                        products.add((String)jsonObjects.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Construct the data source
            //ArrayList<String> categoryArrayList = new ArrayList<>();
            //Create the adapter to convert the array to views
            ProductAdapter adapter = new ProductAdapter(getApplicationContext(),products);
            //Attach the adapter to a ListView
            final ListView listView =(ListView) findViewById(R.id.listView_products);
            listView.setAdapter(adapter);
          /*   listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent= new Intent(context,Products.class);
                    String product =listView.getItemAtPosition(position).toString();
                    Toast.makeText(context, listView.getItemAtPosition(position)+"", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            });*/

        }

    }

}
