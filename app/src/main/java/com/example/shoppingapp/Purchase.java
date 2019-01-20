package com.example.shoppingapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Purchase extends AppCompatActivity {
    static String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        ip = getResources().getString(R.string.ip);
        new Purchase.getPurchase(this).execute("http://"+ip+":9000/Application/getPurchase?username="+Singleton.getEntity().username);

    }
    private class getPurchase extends AsyncTask<String,Void,String> {

        Context context;
        InputStream stream =null;
        String str="";
        String result=null;

        private getPurchase(Context context){
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
    }

