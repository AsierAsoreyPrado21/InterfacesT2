package com.afundacion.gestorfinanzas.rviewclass;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afundacion.gestorfinanzas.R;


import static androidx.constraintlayout.widget.StateSet.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afundacion.gestorfinanzas.rviewclass.Transac;
import com.afundacion.gestorfinanzas.rviewclass.TransacAdapter;
import com.afundacion.gestorfinanzas.rviewclass.TransacList;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RviewTransFragment extends Fragment {

    FragmentTransaction fragmentTransaction;
    private RecyclerView recyclerView;
    private List<Transac> transacs;

    private LinearLayout mainLayout; // Añadimos un nuevo atributo xa implementar snakbar
    private static  String URL="https://63c6654ddcdc478e15c08b47.mockapi.io";
    //AdapterComics adapterComics;
    TransacAdapter transacAdapter;
    private RequestQueue queue;
    private View view;

    private boolean onClickIsOn=true;

    private ProgressBar progressBar;



    public RviewTransFragment() {
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
        View view= inflater.inflate(R.layout.fragment_rview_trans, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        this.queue = Volley.newRequestQueue(getContext());
        this.progressBar=view.findViewById(R.id.progress_circular);
        recyclerView = view.findViewById(R.id.recyclerViewComics);
        transacs = new ArrayList<>();

        requestTransacsList();

        // parseJsonComics();

        //esto es del menu contextual
        registerForContextMenu(recyclerView);

//        while (onClickIsOn){
//            if(ComicsViewHolder.clickCellOn==true){
//
//                getParentFragmentManager().setFragmentResult("key",ComicsViewHolder.infToBeSend);
//                Navigation.findNavController(view).navigate(R.id.detalleComicsFragment);
//
//
//                onClickIsOn=false;
//            }
//
//        }

    }

    private void requestTransacsList() {
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest jarequest = new JsonArrayRequest (
                Request.Method.GET,URL+"/seasons/1/transaction",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        progressBar.setVisibility(View.INVISIBLE);
                        // ar.setVisibility(View.INVISIBLE);

                        //Snackbar.make(mainLayout, "List received", Snackbar.LENGTH_SHORT).show();
                        //Toast.makeText(context, "Hit OK: " + response.getString("status"), Toast.LENGTH_LONG).show();

                        // Parseamos la respuesta y la asignamos a nuestro atributo

                        setTransacs(new TransacList(response));

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

    public TransacList getTransacs() {
        return (TransacList) transacs;
    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int position = -1;
        try {
            position = ((TransacAdapter)recyclerView.getAdapter()).getPosition();
            Log.d("DetalleComicsFragment", "El IDxxxxxxxxxxxxxxx: " + position);
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        //comics.get(position);
//        Comics dataForThisCell= comics.getComics().get(position);
//        String name=dataForThisCell.getPhotoName();
//        Log.d("DetalleComicsFragment", "El IDyyyyyyyyyyyyyyyy: " + position);

        switch (item.getItemId()) {
            case R.id.action_rv_change:
                // do your stuff

//               int x=recyclerView.getChildAdapterPosition(view);
                // Comics dataForThisCell= comics.getComics().get(position);
                //Comics dataForThisCell= comics.get(position);
                //String name=dataForThisCell.getPhotoName();
//                String description = dataForThisCell.getPhotoDescrip();
//                String image_url = dataForThisCell.getPhotoUrl();


                Log.d("DetalleComicsFragment", "El IDyyyyyyyyyyyyyyyy: " + position);
                Log.d("DetalleComicsFragment", "El ID: " + 233333);
                Log.d("DetalleComicsFragment", "El ID: " + "name");

                Toast.makeText(getContext(),"Se modifica este item, nombre " ,
                        Toast.LENGTH_LONG).show();

                //modifyItem(comics, position);


//                int amount =123;
//                String date ="12-3-2023";
//                String operation ="Cost";

//                Bundle bundle =new Bundle();
//                bundle.putString("amount", String.valueOf(amount).toString().trim());
//                //bundle.putString("date", date.getText.toString.trim()); se usa par tomar de un editext p.j.
//                bundle.putString("date", date.trim());
//                bundle.putString("operation", operation.trim());
//                getParentFragmentManager().setFragmentResult("key",bundle );
                //Navigation.findNavController(view).navigate(R.id.modify_Data_Fragment);
                break;
            case R.id.action_rv_delete:
                // do your stuff
                Toast.makeText(getContext(),"Se elimina este item", Toast.LENGTH_LONG).show();
                deleteItem();
                break;
        }
        return super.onContextItemSelected(item);
    }




//    private void modifyItem(List comics, int position){
//        this.comics=comics;
//        Comics dataForThisCell= comics.getComics().get(position);
//        String name=dataForThisCell.getPhotoName();
//        Log.d("DetalleComicsFragment", "El IDyyyyyyyyyyyyyyyy: " + position);
//        Log.d("DetalleComicsFragment", "El ID: " + 233333);
//        Log.d("DetalleComicsFragment", "El ID: " + name);
//
//        Toast.makeText(getContext(),"Se modifica este item, nombre " +name,
//                Toast.LENGTH_LONG).show();
//    }

    private void deleteItem() {
    }

//    private void  parseJsonComics(){
//        queue= Volley.newRequestQueue(getContext());
//        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
//            //JsonObjectRequest queue= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                for (int i = 0; i < response.length(); i++) {
//                    try {
//                        JSONObject comicsObject = response.getJSONObject(i);
//
//                        Comics comic = new Comics();
//                        comic.setPhotoName(comicsObject.getString("name").toString());
//                        comic.setPhotoDescrip(comicsObject.getString("description").toString());
//                        comic.setPhotoUrl(comicsObject.getString("image_url"));
//
//                        comics.add(comic);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }

//                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                adapterComics=new AdapterComics(getContext(),comics);
//                recyclerView.setAdapter(adapterComics);
//
//                adapterComics.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //String photoname = comics.get(recyclerView.getChildAdapterPosition(view)).getPhotoName();
//                        Toast.makeText(v.getContext(), "Do Something With this Click, which name is", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("tag", "onErrorResponse"+ error.getMessage());
//            }
//        });
//
//        queue.add(jsonArrayRequest);
//    }

    public void setTransacs(TransacList transacs) {
//        this.comics = comics;
//        ComicsAdapter myAdapter = new ComicsAdapter (this.comics);
//        recyclerView.setAdapter(myAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        transacAdapter=new TransacAdapter(transacs);
        recyclerView.setAdapter(transacAdapter);

        transacAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x=recyclerView.getChildAdapterPosition(view);
                Transac dataForThisCell= transacs.getTransacs().get(x);
                String dateTrans=dataForThisCell.getDateTrans();
                String typeTrans= dataForThisCell.getTypeTrans();
                String amounTrans = dataForThisCell.getAmounTrans();
                String descripTrans = dataForThisCell.getDescripTrans();

                Toast.makeText(view.getContext(),
                        "Do Something With this Click  "+amounTrans, Toast.LENGTH_SHORT).show();

//
//                Bundle datosAEnviar = new Bundle();
//// Aquí pon todos los datos que quieras en formato clave, valor
//                datosAEnviar.putLong("id", 123L);
//// Y puedes pasarle más datos..
//                datosAEnviar.putInt("edad", 21);
//                datosAEnviar.putString("nombre", "Parzibyte");
//// Preparar el fragmento
//                Fragment fragmento = new DetalleComicsFragment();
//// ¡Importante! darle argumentos
//                fragmento.setArguments(datosAEnviar);
//                Log.d("RviewFragment", "El ID: " + 99999999);
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.content_principal, fragmento);
//                fragmentTransaction.addToBackStack(null);
//
//// Terminar transición y nos vemos en el fragmento de destino
//                fragmentTransaction.commit();

                Bundle infToBeSend =new Bundle();
                infToBeSend.putString("date", dateTrans);
                infToBeSend.putString("transactionType", typeTrans);
                infToBeSend.putString("amount", amounTrans);
                infToBeSend.putString("Description", descripTrans);
//
//                getParentFragmentManager().setFragmentResult("key",infToBeSend);
                //Navigation.findNavController(view).navigate(R.id.detalleTransacsFragment,infToBeSend);
            }
        });

    }

}