package com.afundacion.gestorfinanzas.rviewclass;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afundacion.gestorfinanzas.R;

public class TransacViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

    private TextView transacsTextView1,transacsTextView2, transacsTextView3, transacsTextView4 ;
    private TextView transacsTextView5,transacsTextView6, transacsTextView7, transacsTextView8 ;


    private Transac transacs;

    public static Bundle infToBeSend;
    public static boolean clickCellOn;

    public TransacViewHolder(@NonNull View itemView) {
        super(itemView);
        transacsTextView1=itemView.findViewById(R.id.text_date);
        transacsTextView2=itemView.findViewById(R.id.text_type_trans);
        transacsTextView3=itemView.findViewById(R.id.text_amount);
        transacsTextView4=itemView.findViewById(R.id.text_descrip);
        transacsTextView5=itemView.findViewById(R.id.text_date1);
        transacsTextView6=itemView.findViewById(R.id.text_type_trans1);
        transacsTextView7=itemView.findViewById(R.id.text_amount1);
        transacsTextView8=itemView.findViewById(R.id.text_descrip1);


        itemView.setOnCreateContextMenuListener(this);
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String name = comics.getPhotoName();
//                String description = comics.getPhotoDescrip();
//                String image_url = comics.getPhotoUrl();
//                Context context = v.getContext();
//
//                Toast.makeText(v.getContext(), "Do Something With this Click  "+name, Toast.LENGTH_SHORT).show();
//
//                //Envio de informacion al nuevo fragment
//
//                clickCellOn=true;
//
//                infToBeSend =new Bundle();
//                infToBeSend.putString("nameComics", name.trim());
//                infToBeSend.putString("descripComics", description.trim());
//                infToBeSend.putString("image_urlComics", image_url.trim());
//
//                RviewFragment rviewFragment=new RviewFragment();
//                rviewFragment.changeFragment();
//
//                //getParentFragmentManager().setFragmentResult("key",bundle );
//                //Navigation.findNavController(v).navigate(R.id.de_Fragment);
//
//            }
//        });
    }

    public void bindComics (Transac transacs){
        String data = transacs.getDateTrans();
        this.transacsTextView5.setText(data);
        String typeTransac = transacs.getTypeTrans();
        this.transacsTextView6.setText(typeTransac);
        String amount = transacs.getAmounTrans();
        this.transacsTextView7.setText(amount);
        String description = transacs.getDescripTrans();
        this.transacsTextView8.setText(description);

        //this.transacs=transacs;

    }


    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view,
                                    ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.add(ContextMenu.NONE, R.id.action_rv_change,
                ContextMenu.NONE, R.string.rv_change);
        contextMenu.add(ContextMenu.NONE, R.id.action_rv_delete,
                ContextMenu.NONE, R.string.rv_delete);
    }
}
