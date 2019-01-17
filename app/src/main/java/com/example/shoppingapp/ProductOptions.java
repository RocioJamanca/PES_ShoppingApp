package com.example.shoppingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ProductOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_options);
        Product product=(Product)getIntent().getExtras().getSerializable(("product"));
        Toast.makeText(this,"product",Toast.LENGTH_SHORT);

    }

    public void addToCart(){

    }
    public void addToWishlist(){

    }

}
