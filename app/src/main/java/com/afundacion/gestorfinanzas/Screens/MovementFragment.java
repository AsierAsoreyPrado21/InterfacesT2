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

        try {
            getUserId();
        }catch (AuthFailureError e){}

        //amountTV.setText(totalAmount);
        //Toast.makeText(getActivity(), totalAmount, Toast.LENGTH_LONG).show();

        return layout;
    }


    private void getUserId() throws AuthFailureError {

        //SharedPreferences preferences = getActivity().getSharedPreferences("SESSIONS_APP_PREFS", Context.MODE_PRIVATE);
        //String token = preferences.getString("VALID_TOKEN", null);
        String token = "token 1";


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