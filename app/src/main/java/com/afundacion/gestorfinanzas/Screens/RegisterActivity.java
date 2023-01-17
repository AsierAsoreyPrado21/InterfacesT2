package com.afundacion.gestorfinanzas.Screens;
/*
• Mostrará un formulario de registro .
• Deberá incluir un EditText para ingresar el nombre de usuario.
• Deberá incluir un EditText para ingresar la dirección de correo electrónico.
• Deberá incluir un EditText para ingresar la contraseña.
• Deberá incluir un EditText para validar la contraseña.
• Deberá incluir un botón de tipo "submit" para enviar el formulario.
*/

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestorfinanzas.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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
    public static String url = "https://63c6654ddcdc478e15c08b47.mockapi.io/";

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
                Toast.makeText(context, "User: " +editTextUser.getText().toString(),Toast.LENGTH_SHORT).show();
                PostRegister();
            }
        });requestQueue= Volley.newRequestQueue(this);
    }
    //Peticion Post
    private void PostRegister() {
        JSONObject requestBody = new JSONObject();
        try{
            requestBody.put("user",editTextUser.getText().toString());
            requestBody.put("email",editTextDirection.getText().toString());
            requestBody.put("password",editTextPassword.getText().toString());
            requestBody.put("password",editTextPassword.getText().toString());
        }catch (JSONException e){
            throw new RuntimeException(e);
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                requestBody,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        Toast.makeText(context,"account created",Toast.LENGTH_SHORT).show();
                        finish();
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
