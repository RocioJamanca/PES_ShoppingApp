package com.example.shoppingapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

//Logeamos
public class Login extends AppCompatActivity {

    static String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        Intent intent = getIntent();
        ip = getResources().getString(R.string.ip);
    }

    public void login_click(View view){
        EditText u = (EditText) findViewById(R.id.user_txt);
        EditText p = (EditText) findViewById(R.id.password_txt);
        ProgressBar progressBar=(ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        if(u.getText().toString().equals("")||p.getText().toString().equals("")) {
            Toast.makeText(this,"Sorry you haven't entered all parameters required.",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
        else {
            // http://localhost:9000/Application/register?usernme=rocio&password=rocio
            Singleton.getEntity().username = u.getText().toString();
            new Login.loginFunction(this).execute(u.getText().toString(), p.getText().toString());
        }
    }

    private class loginFunction extends AsyncTask<String,Void,String> {

        Context context;
        InputStream stream = null;
        String str = "";
        String result = null;

        private loginFunction(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strs) {
            InputStream stream = null;
            try {
                URL url = new URL("http://" + ip + ":9000/loginM");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST"); //Utilizamos el método post
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.connect();

                JSONObject obj = new JSONObject();
                obj.put("username", strs[0]);
                obj.put("password", strs[1]);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(obj));
                writer.flush();
                writer.close();


                //Read the response
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                conn.disconnect();
                return sb.toString().split("/")[0];
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //Close stream
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return str;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView message =(TextView) findViewById(R.id.messageText);
            message.setText(result);
            ProgressBar progressBar=(ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);

            if (result.contains("Sorry")) {

                message.setText(result+". Please, try again.");
                EditText u = (EditText) findViewById(R.id.user_txt);
                EditText p = (EditText) findViewById(R.id.password_txt);
                u.getText().clear();
                p.getText().clear();
            }
            else
            {
                Intent intent= new Intent(getApplicationContext(),Shop.class);
                startActivity(intent);
            }
        }
    }


    public String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
 }


