package com.afundacion.gestorfinanzas.rviewclass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TransacList {
    private List<Transac> transacs;

    public TransacList(JSONArray array)  {
        transacs= new ArrayList<>();

        for (int i=0; i< array.length();i++){

            try {
                //JSONObject jsonElement = array.getJSONObject(i);
                //JSONObject comicsObject = response.getJSONObject(i);
                JSONObject transacsObject = array.getJSONObject(i);

//                Comics comic = new Comics();
//                comic.setPhotoName(comicsObject.getString("name").toString());
//                comic.setPhotoDescrip(comicsObject.getString("description").toString());
//                comic.setPhotoUrl(comicsObject.getString("image_url"));
                Transac aTransac =new Transac(transacsObject);
                transacs.add(aTransac);
                //Clip aClip =new Clip(jsonElement);
                //clips.add(aClip);
            }catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public List<Transac> getTransacs() {
        return transacs;
    }
}
