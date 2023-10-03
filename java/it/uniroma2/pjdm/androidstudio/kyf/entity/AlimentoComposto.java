package it.uniroma2.pjdm.androidstudio.kyf.entity;

import android.util.Log;

import java.util.ArrayList;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.uniroma2.pjdm.androidstudio.kyf.utente.home.viewModel.UtenteViewModel;

public class AlimentoComposto extends AlimentoAstratto {

	private static final String TAG =  AlimentoComposto.class.getSimpleName();
	public ArrayList<Pair> alimenti;
	
	public AlimentoComposto(int id, String nome, String descrizione, String creatore, ArrayList<Pair> alimenti) {
		super(id, nome, descrizione, creatore,"alimentoComposto");
		this.alimenti = alimenti;
	}
	
	public AlimentoComposto(String nome, String descrizione, String creatore, ArrayList<Pair> alimenti) {
		super(0, nome, descrizione, creatore,"alimentoComposto");
		this.alimenti = alimenti;
	}

	public ArrayList<Pair> getAlimenti() {
		return alimenti;
	}

	public void setAlimenti(ArrayList<Pair> alimenti) {
		this.alimenti = alimenti;
	}

	public static AlimentoComposto fromJsonObject(JSONObject jsonObject){
		int id;
		try {
			id = jsonObject.getInt("id");
		} catch (JSONException e) {
			id = -1;
		}
		String nome;
		try {
			nome=jsonObject.getString("nome");
		} catch (JSONException e) {
			Log.d(TAG, "fromJsonObject: nome assente");
			throw new RuntimeException(e);
		}
		String descrizione;
		try {
			descrizione = jsonObject.getString("descrizione");
		} catch (JSONException e) {
			descrizione = "";
		}
		String creatore;
		try {
			creatore=jsonObject.getString("creatore");
		} catch (JSONException e) {
			creatore = "";
		}
		// se siamo arrivati fin qui ci mancano solamente gli ingredienti
		ArrayList<Pair> ingredienti = new ArrayList<>();
		try {
			JSONArray jsonArrayIngredienti = jsonObject.getJSONArray("alimenti");

			for(int i = 0; i < jsonArrayIngredienti.length(); i++){
				// creo un nuovo alimento e lo inserisco dentro la lista degli ingredienti
				ingredienti.add(Pair.fromJsonObject(jsonArrayIngredienti.getJSONObject(i)));
			}
		} catch (JSONException e) {
		}
		return new AlimentoComposto(id,nome,descrizione,creatore,ingredienti);
	}
}
