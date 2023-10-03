package it.uniroma2.pjdm.androidstudio.kyf.utente.home.adapter;

import android.os.Bundle;

import java.util.List;

import it.uniroma2.pjdm.androidstudio.kyf.entity.AlimentoAstratto;
import it.uniroma2.pjdm.androidstudio.kyf.entity.Pair;

public class ListElement {
    String nome;
    Double quantita;
    String pasto;
    int id;
    String tipologia;

    public ListElement(String nome, Double quantita, String pasto, int id, String tipologia) {
        this.nome = nome;
        this.quantita = quantita;
        this.pasto = pasto;
        this.id = id;
        this.tipologia = tipologia;
    }
    //             data.add(new ListElement(alimento.getNome(),pair.getQuantita(),categoria,alimento.getId(),alimento.getTipologia() ));

    public ListElement(AlimentoAstratto alimentoAstratto){
        this(alimentoAstratto.getNome(), 0.0, "", alimentoAstratto.getId(), alimentoAstratto.getTipologia());
    }

    public ListElement(Pair pair, String categoria){
        this(pair.getAlimento().getNome(), pair.getQuantita(), categoria, pair.getAlimento().getId(), pair.getAlimento().getTipologia());
    }


    public String getNome() {
        return nome;
    }

    public Double getQuantita() {
        return quantita;
    }

    public String getStringQuantita() {
        return quantita.toString();
    }
    public String getPasto() {
        return pasto;
    }

    public int getId() {
        return id;
    }

    public String getTipologia() {
        return tipologia;
    }

    public Bundle toBundle(){
        Bundle bundle = new Bundle();
        bundle.putString("nome",nome);
        bundle.putDouble("quantita", quantita);
        bundle.putString("pasto",pasto);
        bundle.putInt("id",id);
        bundle.putString("tipologia",tipologia);
        return bundle;
    }

    public static final ListElement fromBundle(Bundle bundle){
        String nome = bundle.getString("nome");
        ListElement listElement = new ListElement(bundle.getString("nome"),bundle.getDouble("quantita"),bundle.getString("pasto"),bundle.getInt("id"),bundle.getString("tipologia"));
        return listElement;
    }
}
