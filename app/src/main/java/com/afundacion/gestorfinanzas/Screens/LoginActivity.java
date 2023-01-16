package com.afundacion.gestorfinanzas.Screens;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestorfinanzas.R;
import com.android.volley.RequestQueue;

/* Mostrará un formulario de inicio de sesión:
• Deberá incluir un EditText para ingresar la dirección de correo electrónico.
• Deberá incluir un EditText para ingresar la contraseña.
• Deberá incluir un botón de tipo "submit" para enviar el formulario.
• Deberá incluir un enlace al registro por si somos nuevos usuarios*/

public class LoginActivity extends AppCompatActivity {
    //Atributos que necesitaremos
    private Context context=this;
    private EditText editTextDireccion;
    private EditText editTextContraseña;
    private Button buttonEnviar;
    private EditText editTextRegistro;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Añadimos los elementos de nuestro xml
        editTextDireccion=findViewById(R.id.direccion);
        editTextContraseña=findViewById(R.id.contraseña);
        buttonEnviar=findViewById(R.id.login);
        editTextRegistro=findViewById(R.id.registro);
        //Proceso que se realiza al clicar en el editText de Registro
        editTextRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "¿No tienes cuenta?", Toast.LENGTH_SHORT).show();
                //Conectamos ese clicado con la actividad de Registro
                Intent intent=new Intent(context,RegisterActivity.class);
                context.startActivity(intent);

            }
        });
        //Proceso al clicar el el boton de Login

    }



}
