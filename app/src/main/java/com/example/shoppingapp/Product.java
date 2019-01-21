package com.example.shoppingapp;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

public class Product {

    public String category; //Aqi dentro ponemos a que pertenece, si es tv, telef, impresora....
    public String model;
    public String brand;
    public double price;
    public String description;
    public int quantity;
    public int id;


    //Clase producto defino esta clase para utilizar luego el adapter
    public Product(JSONObject object) {

    try{
        if(object.length()==3){
            this.quantity=object.getInt("quantity");
            this.id = object.getInt("id");
            object=object.getJSONObject("product");
        }
       this.model = object.getString("model");
       this.brand = object.getString("brand");
       this.category = object.getString("category");
       this.price = object.getDouble("price");
       this.description = object.getString("description");
    }
       catch (JSONException e){
        e.printStackTrace();
       }

    }



}
