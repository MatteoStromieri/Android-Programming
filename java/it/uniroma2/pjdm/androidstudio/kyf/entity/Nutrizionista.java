package it.uniroma2.pjdm.kyf.entity;

import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Nutrizionista {

    private static final String TAG = Nutrizionista.class.getSimpleName();
    public String email;
    public String nome;
    public String cognome;
    public Date dataDiNascita;


    public Nutrizionista(String email, String nome, String cognome, Date dataDiNascita) {
        super();
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Date getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(Date dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Nutrizionista other = (Nutrizionista) obj;
        return Objects.equals(email, other.email);
    }

    public static Nutrizionista fromJsonObject(JSONObject jsonObject){
        try {
            String email = jsonObject.getString("email");
            String nome = jsonObject.getString("nome");
            String cognome = jsonObject.getString("cognome");
            Date dataDiNascita = Date.valueOf(jsonObject.getString("dataDiNascita"));
            return new Nutrizionista(email,nome,cognome,dataDiNascita);
        } catch (JSONException | IllegalArgumentException e) {
            Log.d(TAG, "fromJsonObject: parsing nutrizionista non andato a buonfine");
            throw new RuntimeException(e);
        }
    }

}

