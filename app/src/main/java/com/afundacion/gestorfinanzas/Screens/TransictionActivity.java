package com.afundacion.gestorfinanzas.Screens;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
                    if (!cantidadIng.getText().chars().allMatch((c -> Character.isDigit(c))) || cantidadIng.getText().toString().length() == 0) {
                        //Toast.makeText(context, "En el apartado de Cantidad, debes incluir un número", Toast.LENGTH_LONG).show();
                        cantidadIng.setError("Debes incluir una cantidad numérica");
                        cantidadCorrecta = false;
                    }
                }
                if(description.isEmpty()){
                    //Toast.makeText(context, "Debes incluir una descripción", Toast.LENGTH_LONG).show();
                    descriptionT.setError("Debes incluir una descripción");
                    descCorrecta = false;
                }
                if(date.isEmpty()){
                    //Toast.makeText(context, "Debes incluir una fecha", Toast.LENGTH_LONG).show();
                    dateIns.setError("Debes incluir una fecha");
                    dateCorrecta = false;
                }
                if(cantidadCorrecta && dateCorrecta && descCorrecta){
                    String valorSpinner = (String) spinnerTrans.getSelectedItem();
                    int cantidad = Integer.parseInt(cantidadIng.getText().toString());
                    Toast.makeText(context, valorSpinner, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
