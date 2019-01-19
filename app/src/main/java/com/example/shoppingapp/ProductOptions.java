package com.example.shoppingapp;

import android.content.Context;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        Intent intent = getIntent();
        String model = intent.getStringExtra("model");
        Toast.makeText(this,Singleton.getEntity().username,Toast.LENGTH_SHORT).show();
        ip = getResources().getString(R.string.ip);
        //Before add product to the Cart or wishlist we are going to serch it
        new ProductOptions.findProduct(this).execute("http://"+ip+":9000/Application/findProduct?model="+model);
    }


    public void addToCart(View view){
        TextView textView=(TextView) findViewById(R.id.product_name);
        TextView textView1=(TextView) findViewById(R.id.quantity_text);
       // http://localhost:9000/Application/addPurchase?username=rocio&model=LG1&quantity=2
        new ProductOptions.addToPurchase(this).execute("http://"+ip+":9000/Application/addPurchase?username="+Singleton.getEntity().username+"&model="+textView.getText().toString()+"&quantity="+textView1.getText().toString());
    }
    public void addToWishlist(View view){
        TextView textView=(TextView) findViewById(R.id.product_name);
        new ProductOptions.addToWishlist(this).execute("http://"+ip+":9000/Application/addWishlist?username="+Singleton.getEntity().username+"&model="+ textView.getText().toString());
    }

    private class findProduct extends AsyncTask<String,Void,String> {

        Context context;
        InputStream stream =null;
        String str="";
        String result=null;

        private findProduct(Context context){
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

                Log.i("FindProduct Respones",result);

                return result;

            }
            catch (IOException e){
                return null;
            }
        }
        @Override
        protected void onPostExecute(String result){
            JSONObject jo = null;
            try {
                jo = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String category=jo.get("category").toString();
                String model=jo.get("model").toString();
                String price=jo.get("price").toString();
                String description=jo.get("description").toString();
                String brand=jo.get("brand").toString();


                TextView category_name=(TextView) findViewById(R.id.category_name);
                TextView brand_name=(TextView) findViewById(R.id.brand_name);
                TextView descrption_product=(TextView) findViewById(R.id.decription_product);
                TextView product_name=(TextView) findViewById(R.id.product_name);
                TextView price_name=(TextView) findViewById(R.id.price_name);
                //Precio e imagen
                ImageView imageView= (ImageView)findViewById(R.id.image_product);
                Picasso.get().load("http://"+ip+":9000/Application/showProductImage?model="+model).into(imageView);

                category_name.setText(category);
                brand_name.setText(brand);
                descrption_product.setText(description);
                price_name.setText(price);
                product_name.setText(model);

            } catch (JSONException e) {
                e.printStackTrace();
            };
        }
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

           Toast.makeText(context,result, Toast.LENGTH_SHORT).show();
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

            Toast.makeText(context,result, Toast.LENGTH_SHORT).show();
        }
    }
}
