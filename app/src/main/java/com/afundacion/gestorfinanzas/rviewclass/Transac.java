package com.afundacion.gestorfinanzas.rviewclass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Transac implements Serializable {

    private String photoName;
    private String photoUrl;
    private String photoDescrip;


    public Transac(JSONObject json) throws JSONException {
        this.photoName= json.getString("name");
        this.photoUrl = json.getString("image_url");
        this.photoDescrip = json.getString("description");

    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoDescrip() {
        return photoDescrip;
    }

    public void setPhotoDescrip(String photoDescrip) {
        this.photoDescrip = photoDescrip;
    }
}
