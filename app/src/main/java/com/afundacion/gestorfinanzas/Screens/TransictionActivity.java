package com.afundacion.gestorfinanzas.Screens;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.afundacion.gestorfinanzas.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.InputMismatchException;

public class TransictionActivity extends AppCompatActivity {
    private Context context = this;
    private int amount;
    private String description;
    private String date;
    private String transactionType;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transiction);

        EditText cantidadIng = (EditText) findViewById(R.id.amount);
        EditText descriptionT = (EditText) findViewById(R.id.description);
        EditText dateIns = (EditText) findViewById(R.id.date);
        Spinner spinnerTrans = findViewById(R.id.spinner);
        Button buttonSub = findViewById(R.id.button);
        requestQueue = Volley.newRequestQueue(this);


        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.spinnerOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinnerTrans.setAdapter(adapter);


        buttonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean descCorrecta = true, dateCorrecta = true, cantidadCorrecta = true;
                 description = descriptionT.getText().toString();
                 date = dateIns.getText().toString();

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
                    transactionType = (String) spinnerTrans.getSelectedItem();
                    amount = Integer.parseInt(cantidadIng.getText().toString());
                    registerTransaction();
                }
            }
        });
    }

    private void registerTransaction(){
        JSONObject requestBody = new JSONObject();

        try{
            requestBody.put("amount", amount);
            requestBody.put("description", description);
            requestBody.put("date", date);
            requestBody.put("transactionType", transactionType);

        }catch(JSONException e){
            throw new RuntimeException(e);
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "https://63c6654ddcdc478e15c08b47.mockapi.io/transaction",
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Transacción realizada con éxito", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if ( error.networkResponse == null){
                            Toast.makeText(context, "Imposible conectar al servidor", Toast.LENGTH_SHORT).show();
                        }else {
                            int serverCode = error.networkResponse.statusCode;
                            Toast.makeText(context, "Estado de respuesta: "+ serverCode, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

        );

        this.requestQueue.add(request);
    }

}
