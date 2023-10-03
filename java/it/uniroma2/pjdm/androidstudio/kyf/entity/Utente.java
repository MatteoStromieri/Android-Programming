package it.uniroma2.pjdm.androidstudio.kyf.entity;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.Objects;


import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

import org.json.JSONObject;

    public class Utente {

        private static final String TAG = Utente.class.getSimpleName();
        public String email;

        public String nome;
        public String cognome;
        public Date dataDiNascita;


        public Utente(String email, String nome, String cognome, Date dataDiNascita) {
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
            Utente other = (Utente) obj;
            return Objects.equals(email, other.email);
        }

        public static Utente fromJsonObject(JSONObject jsonObject){
            try {
                String email = jsonObject.getString("email");
                String nome = jsonObject.getString("nome");
                String cognome = jsonObject.getString("cognome");
                Date dataDiNascita = Date.valueOf(jsonObject.getString("dataDiNascita"));
                return new Utente(email,nome,cognome,dataDiNascita);
            } catch (JSONException | IllegalArgumentException e) {
                Log.d(TAG, "fromJsonObject: parsing nutrizionista non andato a buonfine");
                throw new RuntimeException(e);
            }
        }


    }

