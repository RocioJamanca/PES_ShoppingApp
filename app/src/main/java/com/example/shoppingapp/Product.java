package com.example.shoppingapp;

import org.json.JSONException;
import org.json.JSONObject;

public class Product {

    public String category; //Aqi dentro ponemos a que pertenece, si es tv, telef, impresora....
    public String model;
    public String brand;
    public double price;


    public Product(JSONObject object) {
       try{
           this.model=object.getString("model");
           this.brand=object.getString("brand");
           this.category=object.getString("category");
           this.price=object.getDouble("price");
           //
       }
       catch (JSONException e){

       }
    }
}
