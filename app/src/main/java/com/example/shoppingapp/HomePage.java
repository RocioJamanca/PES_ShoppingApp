package com.example.shoppingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

//Pagina de inicio
public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void openLogin (View view){
        Intent intent= new Intent(this,Login.class);
        startActivity(intent);
    }
    public void openRegister (View view){
        Intent intent= new Intent(this,Register.class);
        startActivity(intent);

    }
}
