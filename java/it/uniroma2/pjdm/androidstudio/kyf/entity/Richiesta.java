package it.uniroma2.pjdm.androidstudio.kyf.entity;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;

public class Richiesta {
    private static final String TAG = Richiesta.class.getSimpleName();
    public Date data;
    public String descrizione;
    public Utente mittente;

    public Richiesta(Date data, String descrizione, Utente mittente){
        this.data = data;
        this.descrizione = descrizione;
        this.mittente = mittente;
    }

    public Date getData(){
        return this.data;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Utente getMittente() {
        return mittente;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setMittente(Utente mittente) {
        this.mittente = mittente;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Richiesta richiesta = (Richiesta) object;
        return java.util.Objects.equals(data, richiesta.data) && java.util.Objects.equals(descrizione, richiesta.descrizione) && java.util.Objects.equals(mittente, richiesta.mittente);
    }

    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), data, descrizione, mittente);
    }

    public static Richiesta fromJsonObject(JSONObject jsonObject){
        // se non abbiamo il parametro descrizione non è un problema
        String descrizione;
        try{
            descrizione = jsonObject.getString("descrizione");
        } catch (JSONException e){
            descrizione = "";
        }
        try {
            Utente mittente = Utente.fromJsonObject(jsonObject.getJSONObject("mittente"));
            Date data = Date.valueOf(jsonObject.getString("data"));
            return new Richiesta(data, descrizione, mittente);
        } catch (JSONException | IllegalArgumentException e) {
            Log.d(TAG, "fromJsonObject: parsing richiesta non andato a buon fine");
            throw new RuntimeException(e);
        }
    }

}
