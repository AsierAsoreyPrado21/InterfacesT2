package com.afundacion.gestorfinanzas.graphline;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.afundacion.gestorfinanzas.R;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;


public class GraphDataFragment extends Fragment {



    private static  String URL="https://63c6654ddcdc478e15c08b47.mockapi.io";
    private RequestQueue queue;

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
        View view=inflater.inflate(R.layout.fragment_graph_data, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        this.queue = Volley.newRequestQueue(getContext());
        this.progressBar=view.findViewById(R.id.progress_circular);

        transacs = new ArrayList<>();

        requestTransacsList();


    }
}