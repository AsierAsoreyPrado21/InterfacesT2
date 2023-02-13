package com.afundacion.gestorfinanzas.graph;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import android.widget.ProgressBar;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;

import java.util.ArrayList;


public class GraphDataFragment extends Fragment {

    LineChart lineChart;
    private static  String URL="https://raw.githubusercontent.com/pmalavevfp/Interface22-23/main/API-REST/catalog.json";

    private RequestQueue queue;
    private View view;

    private boolean onClickIsOn=true;

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
        this.progressBar=view.findViewById(R.id.progress_circular);

        //requestComicsList();

        chartShow ();


    }



    private void requestComicsList() {
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest jarequest = new JsonArrayRequest (
                Request.Method.GET,URL,

                //Server.name + "/clips",
                //Server.name + "/clips", // El servidor responderá 404 porque esta URL no existe
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        progressBar.setVisibility(View.INVISIBLE);
                        // ar.setVisibility(View.INVISIBLE);

                        //Snackbar.make(mainLayout, "List received", Snackbar.LENGTH_SHORT).show();
                        //Toast.makeText(context, "Hit OK: " + response.getString("status"), Toast.LENGTH_LONG).show();

                        // Parseamos la respuesta y la asignamos a nuestro atributo
                        //setComics(new ComicsList(response));

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse (VolleyError error) {

                        progressBar.setVisibility(View.INVISIBLE);

                        if (error.networkResponse == null) {
                            // No se ha establecido la conexión
                            //Snackbar.make(mainLayout, "Connection could not be established!", Snackbar.LENGTH_SHORT).show();
                            //Toast.makeText(context, "Connection could not be established!", Toast.LENGTH_LONG).show();
                        }else {
                            // El servidor ha dado una respuesta de error

                            // La siguiente variable contendrá el código HTTP,
                            // por ejemplo 401, 500,...
                            int serverCode = error.networkResponse.statusCode;
                            //Snackbar.make(mainLayout, "Server status: "+String.valueOf(serverCode), Snackbar.LENGTH_SHORT).show();
                            //Toast.makeText(context, "Server status is "+String.valueOf(serverCode), Toast.LENGTH_LONG).show();
                        }

                    }
                }

        );

        this.queue.add(jarequest);
    }

    private void chartShow() {
        ArrayList<String> xDays = new ArrayList<>();
        ArrayList<Entry> yIncomes = new ArrayList<>();
        ArrayList<Entry> yCosts = new ArrayList<>();
//        double x = 0 ;
//        int numDataPoints = 1000;
//        for(int i=0;i<numDataPoints;i++){
//            float sinFunction = Float.parseFloat(String.valueOf(Math.sin(x)));
//            float cosFunction = Float.parseFloat(String.valueOf(Math.cos(x)));
//            x = x + 0.1;
//            yIncomes.add(new Entry(sinFunction,i));
//            yCosts.add(new Entry(cosFunction,i));
//            xDays.add(i, String.valueOf(x));
//        }

        yIncomes.add(new Entry(12,123));
        yIncomes.add(new Entry(13,134));
        yIncomes.add(new Entry(14,145));
        yIncomes.add(new Entry(15,155));
        yIncomes.add(new Entry(16,111));
        yIncomes.add(new Entry(17,121));
        yIncomes.add(new Entry(18,113));


        yCosts.add(new Entry(12,90));
        yCosts.add(new Entry(13,87));
        yCosts.add(new Entry(14,100));
        yCosts.add(new Entry(15,67));
        yCosts.add(new Entry(16,95));
        yCosts.add(new Entry(17,83));
        yCosts.add(new Entry(18,40));

//        String[] xdays = new String[xDays.size()];
//        for(int i=0; i<xDays.size();i++){
//            xdays[i] = xDays.get(i).toString();
//        }
        String [] xdays = {"12","13","14","15","16","17","18"};
        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(yCosts,"Costs");
        lineDataSet1.setDrawCircles(true);
        lineDataSet1.setColor(Color.BLUE);

        LineDataSet lineDataSet2 = new LineDataSet(yIncomes,"Incomes");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.RED);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        lineChart.setData(new LineData(lineDataSets));

        //lineChart.setData(new LineData(xdays,lineDataSets));

        lineChart.setVisibleXRangeMaximum(200f);
    }
    }


