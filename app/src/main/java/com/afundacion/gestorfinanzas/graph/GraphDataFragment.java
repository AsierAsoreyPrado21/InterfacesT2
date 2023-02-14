package com.afundacion.gestorfinanzas.graph;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afundacion.gestorfinanzas.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;

import android.graphics.Color;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class GraphDataFragment extends Fragment {

    LineChart lineChart;
    private static  String URL="https://63c6654ddcdc478e15c08b47.mockapi.io";

    private RequestQueue queue;
    private View view;
    private int typegraph=0;

    private  ArrayList<JSONObject> transacsList= new ArrayList();


    private ProgressBar progressBar;
    public GraphDataFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_graph_data, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        this.queue = Volley.newRequestQueue(getContext());


        lineChart = (LineChart) view.findViewById(R.id.lineChart);
        Button btn1=view.findViewById(R.id.btn1);
        Button btn2=view.findViewById(R.id.btn2);



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context =getContext();
                Toast.makeText(context,"Incomes", Toast.LENGTH_LONG).show();
                typegraph=1;
                try {
                    chartShow(transacsList);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                //chartShow();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"costs", Toast.LENGTH_LONG).show();
                //Navigation.findNavController(view).navigate(R.id.detalleTransacsFragment,infToBeSend);
                //getActivity().onBackPressed();
                typegraph=2;
                try {
                    chartShow(transacsList);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                //chartShow();
            }
        });
        try {
            requestTransacsList();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
            //chartShow();


        //chartShow ();

    }



    private void requestTransacsList() throws JSONException {


        ArrayList<JSONObject> transacs= new ArrayList<>();

        JsonArrayRequest jarequest = new JsonArrayRequest (
                Request.Method.GET,URL+"/seasons/1/transaction",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //Toast.makeText(context, "Hit OK: " + response.getString("status"), Toast.LENGTH_LONG).show();

                        // Parseamos la respuesta y la asignamos a nuestro atributo



                        for (int i=0; i< response.length();i++){

                            try {
                                //JSONObject jsonElement = array.getJSONObject(i);
                                //JSONObject comicsObject = response.getJSONObject(i);
                                JSONObject transacsObject = response.getJSONObject(i);
                                transacsList.add(transacsObject);

                            }catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        try {
                            chartShow(transacsList);
                            //chartShow();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        //chartShow();
                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse (VolleyError error) {

                        if (error.networkResponse == null) {
                            // No se ha establecido la conexión
                           Toast.makeText(getContext(), "Connection could not be established!", Toast.LENGTH_LONG).show();
                        }else {
                            // El servidor ha dado una respuesta de error

                            // La siguiente variable contendrá el código HTTP,
                            // por ejemplo 401, 500,...
                            int serverCode = error.networkResponse.statusCode;
                            Toast.makeText(getContext(), "Server status is "+String.valueOf(serverCode), Toast.LENGTH_LONG).show();
                        }

                    }
                }

        );

        this.queue.add(jarequest);

    }
    private void chartShow(ArrayList<JSONObject> transacsList) throws JSONException {
    //private void chartShow() { //throws JSONException {

        ArrayList<String> xDays = new ArrayList<>();
        ArrayList<Entry> yIncomes = new ArrayList<>();
        ArrayList<Entry> yCosts = new ArrayList<>();


        LocalDate dateAct= LocalDate.now();
        Log.d ("Fecha Actual", String.valueOf(dateAct));

        DateTimeFormatter dtf = DateTimeFormatter. ofPattern("DD/MM/YYYY");

        Period periodo=Period.ofDays(7);

        LocalDate datelimit=dateAct.minus(periodo);

        Log.d ("Fecha 7 dias atras", String.valueOf(datelimit));

        for (int i = 0; i< transacsList.size(); i++) {

            JSONObject jsonObject= transacsList.get(i);

            String date=jsonObject.getString("date");

            LocalDate datearry =LocalDate.parse(date);


            if (datearry.compareTo(datelimit)>0){
                Duration dd= Duration.between(datearry.atStartOfDay(), datelimit.atStartOfDay());

                int dd1=(int) dd.toDays();
                //dd1=dd1*(-1);

                String type=jsonObject.getString("transactionType");

                //String type =transacsList.get (i+1);

                if(type.equalsIgnoreCase("Ingreso")){

                    String amount=jsonObject.getString("amount");

                    //String amount = transacsList.get (i-2);
                    int amount1=Integer.parseInt(amount);
                    yIncomes.add(new Entry(dd1,amount1));
                }


                if(type.equalsIgnoreCase("Gasto")){
                    String amount=jsonObject.getString("amount");
                    //String amount = transacsList.get (i-2);
                    int amount1=Integer.parseInt(amount);
                    yCosts.add(new Entry(dd1,amount1));
                }
                xDays.add(String.valueOf(dd1));
                //dd++;
            }

        }


//        yIncomes.add(new Entry(12,123));
//        yIncomes.add(new Entry(13,134));
//        yIncomes.add(new Entry(14,145));
//        yIncomes.add(new Entry(15,155));
//        yIncomes.add(new Entry(16,111));
//        yIncomes.add(new Entry(17,121));
//        yIncomes.add(new Entry(18,113));
//
//
//        yCosts.add(new Entry(12,90));
//        yCosts.add(new Entry(13,87));
//        yCosts.add(new Entry(14,100));
//        yCosts.add(new Entry(15,67));
//        yCosts.add(new Entry(16,95));
//        yCosts.add(new Entry(17,83));
//        yCosts.add(new Entry(18,40));

        String[] xdays = new String[xDays.size()];
        for(int i=0; i<xDays.size();i++){
            xdays[i] = xDays.get(i); //.toString();
        }
        //String [] xdays = {"12","13","14","15","16","17","18"};
        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(yCosts,"Costs");
        lineDataSet1.setDrawCircles(true);
        lineDataSet1.setColor(Color.BLUE);

        LineDataSet lineDataSet2 = new LineDataSet(yIncomes,"Incomes");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.RED);

//        lineDataSets.add(lineDataSet1);
//        lineDataSets.add(lineDataSet2);

        if (typegraph==2) {
            lineDataSets.add(lineDataSet1);
        }
        if (typegraph==1) {
            lineDataSets.add(lineDataSet2);

        }

        if (typegraph==0){
            lineDataSets.add(lineDataSet1);
            lineDataSets.add(lineDataSet2);}

        lineChart.setData(new LineData(lineDataSets));

        //lineChart.setData(new LineData(xdays,lineDataSets));

        lineChart.setVisibleXRangeMaximum(20f);
        typegraph=0;
        lineChart.invalidate();

    }
    }


