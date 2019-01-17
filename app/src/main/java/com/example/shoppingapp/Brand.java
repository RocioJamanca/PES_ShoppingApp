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

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Brand extends AppCompatActivity {
 static String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);

        ip = getResources().getString(R.string.ip);
        //new findCategories(this).execute("http://ip:9000/Application/findCategorries");
        new Brand.findBrands(this).execute("http://"+ip+":9000/Application/findBrands");

    }

    private class findBrands extends AsyncTask<String,Void,String> {

        Context context;
        InputStream stream =null;
        String str="";
        String result=null;

        private findBrands(Context context){
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

                Log.i("Brands Respones",result);

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
            ArrayList<String> brands = new ArrayList<String>();
            try {
                jsonObjects = new JSONArray(result);
                for (int i = 0; i < jsonObjects.length(); i++) {
                    try {
                        brands.add((String)jsonObjects.get(i));
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
            BrandAdapter adapter = new BrandAdapter(getApplicationContext(),brands);
            //Attach the adapter to a ListView
            final ListView listView =(ListView) findViewById(R.id.list_brand);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent= new Intent(context,Products.class);
                    String brand = listView.getItemAtPosition(position).toString();
                    //Toast.makeText(context, listView.getItemAtPosition(position)+"", Toast.LENGTH_SHORT).show();
                    intent.putExtra("brand",brand);
                    startActivity(intent);
                }
            });
        }
    }


}
