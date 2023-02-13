package com.afundacion.gestorfinanzas.Launcher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestorfinanzas.Screens.LoginActivity;
import com.afundacion.gestorfinanzas.Screens.RegisterActivity;

public class LauncherActivity extends AppCompatActivity {
    private Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Toast.makeText(context, "Abriendo la aplicacion...", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context , LoginActivity.class);
        context.startActivity(intent);
    }
}