package com.afundacion.gestorfinanzas.rviewclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afundacion.gestorfinanzas.R;


public class TransacAdapter extends RecyclerView.Adapter<TransacViewHolder> implements View.OnClickListener{
    private TransacList transacsToBoShow;

    private View.OnClickListener listener;

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    ///////////////////MCont
    private Context context;
    private PopupMenu popupMenu;



    public TransacAdapter(TransacList comics){
        this.transacsToBoShow=comics;
    }

    @NonNull
    @Override
    public TransacViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.comic_cell,parent,false);
        view.setOnClickListener(this);
        TransacViewHolder comicsViewHolder=new TransacViewHolder(view);
        return comicsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransacViewHolder holder, int position) {

        Transac dataForThisCell= this.transacsToBoShow.getComics().get(position);
        holder.bindComics(dataForThisCell);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getLayoutPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.transacsToBoShow.getComics().size();
    }

    public void setOnClickListener (View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }
}
