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

public class ProductOptions extends AppCompatActivity {
    static String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_options);
        Product product=(Product)getIntent().getExtras().getSerializable(("product")); //PASAR BIEN EL PRODUCTO
        Toast.makeText(this,"product",Toast.LENGTH_SHORT);
        ip = getResources().getString(R.string.ip);
    }

    public void addToCart(View view){
        new ProductOptions.addToPurchase(this).execute("http://"+ip+":9000/Application/addPurchase?email=rociojamanca@hotmail&model=ENVY&quantity=4");
    }
    public void addToWishlist(View view){
        new ProductOptions.addToWishlist(this).execute("http://"+ip+":9000/Application/addWishlist?email=rociojamanca@hotmail&model=ENVY");

    }


    private class addToPurchase extends AsyncTask<String,Void,String> {

        Context context;
        InputStream stream =null;
        String str="";
        String result=null;

        private addToPurchase(Context context){
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

                Log.i("Purchase Respones",result);

                return result;

            }
            catch (IOException e){
                return null;
            }
        }
        @Override
        protected void onPostExecute(String result){

           Toast.makeText(context,result, Toast.LENGTH_SHORT);
        }
    }



    private class addToWishlist extends AsyncTask<String,Void,String> {

        Context context;
        InputStream stream =null;
        String str="";
        String result=null;

        private addToWishlist(Context context){
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

                Log.i("Wishlist Respones",result);

                return result;

            }
            catch (IOException e){
                return null;
            }
        }
        @Override
        protected void onPostExecute(String result){

            Toast.makeText(context,result, Toast.LENGTH_SHORT);
        }
    }
}
