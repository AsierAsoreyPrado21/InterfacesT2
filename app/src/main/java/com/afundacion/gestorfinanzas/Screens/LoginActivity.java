package com.afundacion.gestorfinanzas.Screens;

import static android.widget.Toast.LENGTH_SHORT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import java.sql.DriverPropertyInfo;

/* Mostrará un formulario de inicio de sesión:
• Deberá incluir un EditText para ingresar la dirección de correo electrónico.
• Deberá incluir un EditText para ingresar la contraseña.
• Deberá incluir un botón de tipo "submit" para enviar el formulario.
• Deberá incluir un enlace al registro por si somos nuevos usuarios*/

public class LoginActivity extends AppCompatActivity {
    //Atributos que necesitaremos
    private Context context=this;
    private EditText editTextDirection;
    private EditText editTextPassword;
    private Button buttonSend;
    private TextView textViewRegister;
    private RequestQueue queue;
    public static String url = "https://63c6654ddcdc478e15c08b47.mockapi.io";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Añadimos los elementos de nuestro xml
        editTextDirection=findViewById(R.id.direction);
        editTextPassword=findViewById(R.id.password);
        buttonSend=findViewById(R.id.login);
        textViewRegister=findViewById(R.id.register);
        //Proceso que se realiza al clicar en el editText de Registro
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "¿No have account?", LENGTH_SHORT).show();
                //Conectamos ese clicado con la actividad de Registro
                Intent intent=new Intent(context,RegisterActivity.class);
                context.startActivity(intent);

            }
        });
        //Proceso al clicar el el boton de Login
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendPostRequest();

            }
        });queue= Volley.newRequestQueue(this);
    }
    //Peticion Get
    private void sendPostRequest() {

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url+"/seasons?email="+editTextDirection.getText().toString(),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String receivedToken;
                        JSONObject usuario;
                        try {

                            usuario = response.getJSONObject(0);
                            receivedToken=usuario.getString("token");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Toast.makeText(context, "Token " + receivedToken, Toast.LENGTH_SHORT).show();
                        SharedPreferences preferences = context.getSharedPreferences("SESSIONS_APP_PREFS", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("VALID_EMAIL", editTextDirection.getText().toString());
                        editor.putString("VALID_TOKEN", receivedToken);
                        editor.commit();
                        finish();


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
        }

        );this.queue.add(request);
    }


}
