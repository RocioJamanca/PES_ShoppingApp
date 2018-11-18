package com.example.shoppingapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        Intent intent = getIntent();

    }

   public void loginToServer(View view) //Hacemos la funcion para poder conectar al servido y conseguir hacer el login
    {
        new Thread(new Runnable() {
            InputStream stream = null;
            String str="";
            String result =null;
            Handler handler = new Handler(); //Lo hacemos para crear un canal de acceso con el main Thread

            @Override
            public void run() {
                try{

                    EditText u = (EditText) findViewById(R.id.user_txt);
                    EditText p = (EditText) findViewById(R.id.passwordR_txt);

                    // http://localhost:9000/Application/login?usernme=rocio&password=rocio
                    String query =String.format("http://192.168.1.44:9000/Application/loginM?username="+u.getText().toString()+"&password="+p.getText().toString());
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

                            TextView message =(TextView) findViewById(R.id.messageText);
                            message.setText(result);


                            if (result.contains("Sorry")) {

                                message.setText(result+". Please, try again.");
                                EditText u = (EditText) findViewById(R.id.user_txt);
                                EditText p = (EditText) findViewById(R.id.passwordR_txt);
                                u.getText().clear();
                                p.getText().clear();

                            }
                            else
                            {
                                Button btn= (Button) findViewById(R.id.enter_btn);
                                btn.setVisibility(View.VISIBLE);
                            }

                        }

                    });


                } catch (Exception e){
                    e.printStackTrace();
                }



            }



        }) .start();





    }


    public void openShop (View view){
        Intent intent= new Intent(this,Shop.class);
        startActivity(intent);

    }

}
