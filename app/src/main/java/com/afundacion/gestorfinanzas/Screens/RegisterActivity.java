package com.afundacion.gestorfinanzas.Screens;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import com.afundacion.gestorfinanzas.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    //Atributos
    private EditText editTextUser;
    private EditText editTextDirection;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button buttonRegister;
    private RequestQueue requestQueue;
    private Context context=this;
    public static String url = "https://63c6654ddcdc478e15c08b47.mockapi.io";///users/?email=
    private boolean condicion = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Inicializacion de la parte de XML
        editTextUser=findViewById(R.id.user);
        editTextDirection=findViewById(R.id.direction);
        editTextPassword=findViewById(R.id.password);
        editTextConfirmPassword=findViewById(R.id.passwordConfirm);
        buttonRegister=findViewById(R.id.register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextUser.length()==0){
                    editTextUser.setError("Usuario invalido");
                    condicion=false;
                }
                else if(editTextDirection.length()==0) {
                    editTextDirection.setError("Campo Vacío");
                    condicion=false;
                }
                else if(editTextPassword.length()==0) {
                    editTextPassword.setError("Escriba una contraseña");
                    condicion=false;
                }
                else if(editTextConfirmPassword.length()==0) {
                    editTextConfirmPassword.setError("Contraseña invalida");

                    condicion=false;
                }
                else if(!editTextConfirmPassword.getText().toString().equals(editTextPassword.getText().toString())) {
                    editTextPassword.setError("Las contraseñas no coinciden");
                    condicion=false;
                }
                else {
                    condicion = true;
                }
                if(condicion) {
                    GetEmail();
                }
            }
        });
    }
    //Peticion Get
    private void GetEmail(){
        requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest get = new JsonArrayRequest(
                Request.Method.GET,
                url + "/seasons?email=" + editTextDirection.getText().toString(),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length()==0) {
                            Toast.makeText(context, "Usuario no registrado", Toast.LENGTH_SHORT).show();
                            PostRegister();
                        }

                        else {
                            Toast.makeText(context, "Usuario ya registrado", Toast.LENGTH_SHORT).show();
                            editTextDirection.setError("Email ya registrado");
                            //condicion = false;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse == null) {
                    Toast.makeText(context, "Failed request",Toast.LENGTH_LONG).show();
                }
                else {
                    int serverCode = error.networkResponse.statusCode;
                    Toast.makeText(context, "response status: "+serverCode,Toast.LENGTH_LONG).show();
                }
            }
        });this.requestQueue.add(get);
    }
    //Peticion Post
    private void PostRegister() {
        JSONObject requestBody = new JSONObject();
        try {

            requestBody.put("user", editTextUser.getText().toString());
            requestBody.put("email", editTextDirection.getText().toString());
            requestBody.put("password", editTextPassword.getText().toString());
            requestBody.put("Confirm password", editTextConfirmPassword.getText().toString());


        }catch (JSONException e){
            throw new RuntimeException(e);
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url+"/seasons",
                requestBody,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        Toast.makeText(context,"account created",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse==null){
                    Toast.makeText(context,"failed request",Toast.LENGTH_SHORT).show();

                }
                else{
                    int serverCode=error.networkResponse.statusCode;
                    Toast.makeText(context, "response status: "+serverCode, Toast.LENGTH_SHORT).show();
                }
            }
        }
        );this.requestQueue.add(request);
    }
}