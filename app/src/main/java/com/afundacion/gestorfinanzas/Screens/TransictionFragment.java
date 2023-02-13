package com.afundacion.gestorfinanzas.Screens;

import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afundacion.gestorfinanzas.DatePicker;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransictionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransictionFragment extends Fragment {

    private int amount;
    private String description;
    private String date;
    private String transactionType;
    private RequestQueue requestQueue;
    private int userId;
    private TextView dateIns;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TransictionFragment(){

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransictionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransictionFragment newInstance(String param1, String param2) {
        TransictionFragment fragment = new TransictionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        requestQueue = Volley.newRequestQueue(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.activity_transiction, container, false);


        EditText cantidadIng = (EditText) layout.findViewById(R.id.amount);
            EditText descriptionT = (EditText) layout.findViewById(R.id.description);
            dateIns = (TextView) layout.findViewById(R.id.date);
            Spinner spinnerTrans = layout.findViewById(R.id.spinner);
            Button buttonSub = layout.findViewById(R.id.button);

            //dateIns.setText(String.valueOf(LocalDate.now()));

            ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getActivity(), R.array.spinnerOptions, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

            spinnerTrans.setAdapter(adapter);


            buttonSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean descCorrecta = true, dateCorrecta = true, cantidadCorrecta = true;
                    description = descriptionT.getText().toString();
                    date = dateIns.getText().toString();

                    if (!cantidadIng.getText().chars().allMatch((c -> Character.isDigit(c))) || cantidadIng.getText().toString().length() == 0) {
                        //Toast.makeText(context, "En el apartado de Cantidad, debes incluir un número", Toast.LENGTH_LONG).show();
                        cantidadIng.setError("Debes incluir una cantidad numérica");
                        cantidadCorrecta = false;
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
                        getUserId();
                    }
                }
            });
        // Inflate the layout for this fragment

        dateIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.date:
                        showDatePickerDialog();
                        break;
                }
            }
        });

        return layout;
    }

    private void showDatePickerDialog(){
        DatePicker newFragment = DatePicker.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
                String monthCambiado = String.valueOf(month+1), dayCambiado= String.valueOf(day);
                if(month < 10){
                    monthCambiado = "0"+(month+1);
                }
                if(day < 10){
                    dayCambiado = "0"+day;
                }
                final String selectedDate = year + "-"+ monthCambiado+ "-"+dayCambiado;
                dateIns.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    private void getUserId(){

        SharedPreferences preferences = getActivity().getSharedPreferences("SESSIONS_APP_PREFS", Context.MODE_PRIVATE);
        String token = preferences.getString("VALID_TOKEN", null);
        //String token = "token 1";

        if(token != null) {
            JsonArrayRequest request = new JsonArrayRequest(
                    Request.Method.GET,
                    "https://63c6654ddcdc478e15c08b47.mockapi.io/seasons?token=" + token,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {

                                JSONObject usuario = response.getJSONObject(0);
                                userId = usuario.getInt("id");
                                registerTransaction();

                            } catch (JSONException | NullPointerException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.networkResponse == null) {
                                Toast.makeText(getActivity(), "Imposible conectar al servidor", Toast.LENGTH_SHORT).show();
                            } else {
                                int serverCode = error.networkResponse.statusCode;
                                Toast.makeText(getActivity(), "Estado de respuesta: " + serverCode, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
            this.requestQueue.add(request);
        }else{
            Toast.makeText(getActivity(), "non furrula", Toast.LENGTH_LONG).show();
        }
    }

    private void registerTransaction(){

        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("amount", amount);
            requestBody.put("description", description);
            requestBody.put("date", date);
            requestBody.put("transactionType", transactionType);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        String url = "https://63c6654ddcdc478e15c08b47.mockapi.io/seasons/"+userId+"/transaction";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity(), "Transacción realizada con éxito", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse == null) {
                            Toast.makeText(getActivity(), "Imposible conectar al servidor", Toast.LENGTH_SHORT).show();
                        } else {
                            int serverCode = error.networkResponse.statusCode;
                            Toast.makeText(getActivity(), "Estado de respuesta: " + serverCode, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

        );
        this.requestQueue.add(request);
    }
}