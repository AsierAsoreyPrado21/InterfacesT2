package com.afundacion.gestorfinanzas.Screens;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afundacion.gestorfinanzas.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovementFragment extends Fragment {

    private TextView amountTV;
    private RequestQueue queue;
    private TextView amount1;
    private TextView amount2;
    private TextView amount3;
    private TextView amount4;
    private TextView amount5;
    private TextView amount6;
    private TextView amount7;
    private TextView type1;
    private TextView type2;
    private TextView type3;
    private TextView type4;
    private TextView type5;
    private TextView type6;
    private TextView type7;
    private int totalAmount = 0;
    private int userId;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovementFragment newInstance(String param1, String param2) {
        MovementFragment fragment = new MovementFragment();
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

        queue = Volley.newRequestQueue(getActivity());
        //takeData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.movement_fragment, container, false);

        amountTV = layout.findViewById(R.id.amount);
        amount1 = layout.findViewById(R.id.amount1);
        amount2 = layout.findViewById(R.id.amount2);
        amount3 = layout.findViewById(R.id.amount3);
        amount4 = layout.findViewById(R.id.amount4);
        amount5 = layout.findViewById(R.id.amount5);
        amount6 = layout.findViewById(R.id.amount6);
        amount7 = layout.findViewById(R.id.amount7);
        type1 = layout.findViewById(R.id.type1);
        type2 = layout.findViewById(R.id.type2);
        type3 = layout.findViewById(R.id.type3);
        type4 = layout.findViewById(R.id.type4);
        type5 = layout.findViewById(R.id.type5);
        type6 = layout.findViewById(R.id.type6);
        type7 = layout.findViewById(R.id.type7);

        try {
            getUserId();
        }catch (AuthFailureError e){}

        return layout;
    }


    private void getUserId() throws AuthFailureError {

        SharedPreferences preferences = getActivity().getSharedPreferences("SESSIONS_APP_PREFS", Context.MODE_PRIVATE);
        String token = preferences.getString("VALID_TOKEN", null);
        //String token = "token 1";


        if (token != null) {

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
                                takeData();

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
            queue.add(request);
        }else{
            Toast.makeText(getActivity(), "No furrula", Toast.LENGTH_LONG).show();
            throw new AuthFailureError();
        }
    }

    private void takeData(){

        String url = "https://63c6654ddcdc478e15c08b47.mockapi.io/seasons/"+userId+"/transaction";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int amount = jsonObject.getInt("amount");
                                String type = jsonObject.getString("transactionType");

                                if (type.equalsIgnoreCase("Ingreso")) {
                                    totalAmount += amount;
                                } else {
                                    totalAmount -= amount;
                                }

                            }

                            amountTV.setText(String.valueOf(totalAmount));

                            int veces = 0;

                            if(response.length() > 6){
                                for(int i = response.length()-1; i > response.length()-8; i--){

                                    JSONObject jsonObject2 = response.getJSONObject(i);

                                    switch (veces){
                                        case 0:
                                            amount1.setText(String.valueOf(jsonObject2.getInt("amount")));
                                            type1.setText(jsonObject2.getString("transactionType"));
                                            break;
                                        case 1:
                                            amount2.setText(String.valueOf(jsonObject2.getInt("amount")));
                                            type2.setText(jsonObject2.getString("transactionType"));
                                            break;
                                        case 2:
                                            amount3.setText(String.valueOf(jsonObject2.getInt("amount")));
                                            type3.setText(jsonObject2.getString("transactionType"));
                                            break;
                                        case 3:
                                            amount4.setText(String.valueOf(jsonObject2.getInt("amount")));
                                            type4.setText(jsonObject2.getString("transactionType"));
                                            break;
                                        case 4:
                                            amount5.setText(String.valueOf(jsonObject2.getInt("amount")));
                                            type5.setText(jsonObject2.getString("transactionType"));
                                            break;
                                        case 5:
                                            amount6.setText(String.valueOf(jsonObject2.getInt("amount")));
                                            type6.setText(jsonObject2.getString("transactionType"));
                                            break;
                                        case 6:
                                            amount7.setText(String.valueOf(jsonObject2.getInt("amount")));
                                            type7.setText(jsonObject2.getString("transactionType"));
                                            break;

                                    }
                                    veces++;
                                }
                            }else{

                                amount1.setVisibility(View.GONE);
                                type1.setVisibility(View.GONE);
                                amount2.setVisibility(View.GONE);
                                type2.setVisibility(View.GONE);
                                amount3.setVisibility(View.GONE);
                                type3.setVisibility(View.GONE);
                                amount4.setVisibility(View.GONE);
                                type4.setVisibility(View.GONE);
                                amount5.setVisibility(View.GONE);
                                type5.setVisibility(View.GONE);
                                amount6.setVisibility(View.GONE);
                                type6.setVisibility(View.GONE);
                                amount7.setVisibility(View.GONE);
                                type7.setVisibility(View.GONE);

                                for(int i = 0; i < response.length(); i++){

                                    JSONObject jsonObject2 = response.getJSONObject(i);

                                    switch (i){
                                        case 6:
                                            amount1.setVisibility(View.VISIBLE);
                                            type1.setVisibility(View.VISIBLE);
                                            amount1.setText(String.valueOf(jsonObject2.getInt("amount")));
                                            type1.setText(jsonObject2.getString("transactionType"));
                                            break;
                                        case 5:
                                            amount2.setVisibility(View.VISIBLE);
                                            type2.setVisibility(View.VISIBLE);
                                            amount2.setText(String.valueOf(jsonObject2.getInt("amount")));
                                            type2.setText(jsonObject2.getString("transactionType"));
                                            break;
                                        case 24:
                                            amount3.setVisibility(View.VISIBLE);
                                            type3.setVisibility(View.VISIBLE);
                                            amount3.setText(String.valueOf(jsonObject2.getInt("amount")));
                                            type3.setText(jsonObject2.getString("transactionType"));
                                            break;
                                        case 3:
                                            amount4.setVisibility(View.VISIBLE);
                                            type4.setVisibility(View.VISIBLE);
                                            amount4.setText(String.valueOf(jsonObject2.getInt("amount")));
                                            type4.setText(jsonObject2.getString("transactionType"));
                                            break;
                                        case 2:
                                            amount5.setVisibility(View.VISIBLE);
                                            type5.setVisibility(View.VISIBLE);
                                            amount5.setText(String.valueOf(jsonObject2.getInt("amount")));
                                            type5.setText(jsonObject2.getString("transactionType"));
                                            break;
                                        case 1:
                                            amount6.setVisibility(View.VISIBLE);
                                            type6.setVisibility(View.VISIBLE);
                                            amount6.setText(String.valueOf(jsonObject2.getInt("amount")));
                                            type6.setText(jsonObject2.getString("transactionType"));
                                            break;
                                        case 0:
                                            amount7.setVisibility(View.VISIBLE);
                                            type7.setVisibility(View.VISIBLE);
                                            amount7.setText(String.valueOf(jsonObject2.getInt("amount")));
                                            type7.setText(jsonObject2.getString("transactionType"));
                                            break;

                                    }
                                }
                            }


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
        queue.add(request);
    }

}