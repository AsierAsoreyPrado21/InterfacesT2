package com.afundacion.gestorfinanzas.Screens;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afundacion.gestorfinanzas.R;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.InputMismatchException;

public class TransictionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transiction);

        EditText cantidadIng = (EditText) findViewById(R.id.amount);
        EditText descriptionT = (EditText) findViewById(R.id.description);
        EditText dateIns = (EditText) findViewById(R.id.date);
        Spinner spinnerTrans = findViewById(R.id.spinner);
        Button buttonSub = findViewById(R.id.button);

        Context context = this;

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.spinnerOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinnerTrans.setAdapter(adapter);

        buttonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean descCorrecta = true, dateCorrecta = true, cantidadCorrecta = true;
                String description = descriptionT.getText().toString();
                String date = dateIns.getText().toString();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (!cantidadIng.getText().chars().allMatch((c -> Character.isDigit(c)))) {
                        Toast.makeText(context, "En el apartado de Cantidad, debes incluir un número", Toast.LENGTH_LONG).show();
                        cantidadCorrecta = false;
                    }
                }
                if(description.length() == 0){
                    Toast.makeText(context, "Debes incluir una descripción", Toast.LENGTH_LONG).show();
                    descCorrecta = false;
                }
                if(date.length() == 0){
                    Toast.makeText(context, "Debes incluir una fecha", Toast.LENGTH_LONG).show();
                    dateCorrecta = false;
                }
                if(cantidadCorrecta && dateCorrecta && descCorrecta){
                    int cantidad = Integer.parseInt(cantidadIng.getText().toString());
                    Toast.makeText(context, "Enviando Formulario...", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
