package com.example.shoppingapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent intent = getIntent();

    }

    public void loginToServer(View view) //Hacemos la funcion para poder conectar al servido y conseguir hacer el login
    {
        new Thread(new Runnable() {
            InputStream stream = null;
            String str="";
            String result =null;
            Handler handler = new Handler(); //Lo hacemos para crear un canal de acceso con el main Thread

            public void run() {
                try{

                    EditText u = (EditText) findViewById(R.id.userR_txt);
                    EditText p = (EditText) findViewById(R.id.passwordR_txt);
                    EditText e= (EditText) findViewById(R.id.emailR_txt);

                    // http://localhost:9000/Application/login?usernme=rocio&password=rocio
                    String query =String.format("http://192.168.1.44:9000/Application/registerM?username="+u.getText().toString()+"&email="+e.getText().toString()+"&password="+p.getText().toString());
                    URL url = new URL(query);
                    //Libreria
                    HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                    //Condiciones de la conexion
                    connection.setReadTimeout(10000); //Timeout
                    connection.setConnectTimeout(15000); //Timeout
                    connection.setRequestMethod("GET");//Definimos el metodo
                    connection.setDoInput(true);//Queremos recibir la respuesta
                    connection.connect(); //Conexion

                    //Stream de respuesta
                    stream = connection.getInputStream();
                    //Para respuesta
                    BufferedReader reader =null;
                    StringBuilder stringBuilder = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(stream));

                    String line=null;
                    while ((line=reader.readLine()) != null){ //Mientras haya datos en la conexion
                        stringBuilder.append(line);
                    }

                    //Para mostrar el resultado en el TextView
                    result=stringBuilder.toString();
                    Log.i("Login to server result",result);
                    handler.post(new Runnable() {
                        public void run() {

                            TextView message =(TextView) findViewById(R.id.messsageTextR);
                            message.setText(result);


                        }

                    });


                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        }) .start();

        TextView message =(TextView) findViewById(R.id.messsageTextR);
        message.setText(message.toString());
        if (message.getText().toString()=="Congratulations! You've been registered correctly") {
            message.setText(message.toString());
        }
        else
        {
            message.setText(message.toString());
        }

    }
}
