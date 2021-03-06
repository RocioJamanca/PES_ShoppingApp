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

//Muestra la lista de los productos que tenemos en la "lista de deseos"(wishlist)
public class Wishlist extends AppCompatActivity {
    static String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        ip = getResources().getString(R.string.ip);
        new Wishlist.getWishlist(this).execute("http://"+ip+":9000/Application/getWishlist?username="+Singleton.getEntity().username);
    }
    private class getWishlist extends AsyncTask<String,Void,String> {

        Context context;
        InputStream stream =null;
        String str="";
        String result=null;

        private getWishlist(Context context){
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
            if(result.equals("[]")){
                findViewById(R.id.no_wishlist).setVisibility(View.VISIBLE);
                return;
            }
            findViewById(R.id.no_wishlist).setVisibility(View.INVISIBLE);
            //Add item to adapter
            // Factory method to convert an array of JSON objects into a list of objects
            JSONArray jsonObjects = null;
            final ArrayList<Product> products = new ArrayList<Product>();
            try {
                jsonObjects = new JSONArray(result);
                for (int i = 0; i < jsonObjects.length(); i++) {
                    try {
                        products.add(new Product(jsonObjects.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Construct the data source
            //Create the adapter to convert the array to views
            ProductAdapter adapter = new ProductAdapter(context,products);
            //Attach the adapter to a ListView
            final ListView listView =(ListView) findViewById(R.id.wish_listView);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent= new Intent(context,ProductOptions.class);
                    String product =listView.getItemAtPosition(position).toString();
                    String model=products.get(position).model;
                    intent.putExtra("model",model);
                    startActivity(intent);
                }
            });
        }
    }

}