package com.afundacion.gestorfinanzas.rviewclass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Transac implements Serializable {

    private String dateTrans;
    private String typeTrans;
    private String amounTrans;
    private String descripTrans;


    public Transac(JSONObject json) throws JSONException {
        this.dateTrans= json.getString("date");
        this.typeTrans= json.getString("transactionType");
        this.amounTrans= json.getString("amount");
        this.descripTrans = json.getString("description");

    }

    public String getDateTrans() {
        return dateTrans;
    }

    public void setDateTrans(String dateTrans) {
        this.dateTrans = dateTrans;
    }

    public String getTypeTrans() {
        return typeTrans;
    }

    public void setTypeTrans(String typeTrans) {
        this.typeTrans = typeTrans;
    }

    public String getAmounTrans() {
        return amounTrans;
    }

    public void setAmounTrans(String amounTrans) {
        this.amounTrans = amounTrans;
    }

    public String getDescripTrans() {
        return descripTrans;
    }

    public void setDescripTrans(String descripTrans) {
        this.descripTrans = descripTrans;
    }
}
