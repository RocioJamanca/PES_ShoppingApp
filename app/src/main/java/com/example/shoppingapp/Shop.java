package com.example.shoppingapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Shop extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        Intent intent = getIntent();

    }

    public void wishlistT(View view){
        Intent intent= new Intent(this,Wishlist.class);
        startActivity(intent);
    }
    public void purchase(View view){
        Intent intent= new Intent(this,Purchase.class);
        startActivity(intent);
    }

    public void openSelection(View view){
        switch ( view.getId()){
            case R.id.categories:
                Intent intent= new Intent(this,Category.class);
                startActivity(intent);
                break;
            case R.id.brands:
                Intent intent1= new Intent(this,Brand.class);
                startActivity(intent1);
                break;
            case R.id.whishlist:
                Intent intent2= new Intent(this,Wishlist.class);
                startActivity(intent2);
                break;
                





        }


    }

}
