package it.uniroma2.pjdm.androidstudio.kyf.entity;

import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Pasto {

	public Date data;
	public String categoria;
	public ArrayList<Pair> alimenti;
	private static final String TAG = Pasto.class.getSimpleName();
	
	public Pasto(Date data, String categoria, ArrayList<Pair> alimenti) {
		super();
		this.data = data;
		this.categoria = categoria;
		this.alimenti = alimenti;
	}

	public Pasto(Date data, String categoria) {
		this(data,categoria,new ArrayList<Pair>());
	}
	
	public void addAlimento(AlimentoAstratto alimento, double quantita) {
		alimenti.add(new Pair(alimento,quantita));
	}
	
	public void removeAlimento(AlimentoAstratto alimento) {
		alimenti.remove(new Pair(alimento));
	}
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	/*
	 * This method is used to convert a Pasto instance to a JSONObject containing a JSONArray of Pasto
	 * */
	public ArrayList<Pair> getAlimenti() {
		return alimenti;
	}

	static public Pasto fromJsonObject(JSONObject jsonObject){
		// vediamo se sono presenti alimenti, data e categoria
		JSONArray alimenti;
		Date data;
		String categoria;

		try {
			alimenti = jsonObject.getJSONArray("alimenti");
		} catch (JSONException e) {
			Log.d(TAG, "fromJsonObject: Alimenti non presenti in pasto");
			throw new RuntimeException(e);
		}
		try {
			data = Date.valueOf(jsonObject.getString("data"));
		} catch(JSONException | IllegalArgumentException e) {
			Log.d(TAG, "fromJsonObject: data non presenti o mal formata in pasto");
			throw new RuntimeException(e);
		}
		try {
			categoria = jsonObject.getString("categoria");
		} catch (JSONException e){
			Log.d(TAG,"fromJsonObject: categoria non presente in Pasto");
			throw new RuntimeException(e);
		}

		// ora posso creare l'array di pasti
		ArrayList<Pair> alimentiMangiati = new ArrayList<>();

		// iteriamo sopra il jsonarray di alimenti per creare i pair
		try {

			for(int i = 0; i < alimenti.length(); i++){
				// creo un nuovo alimento e lo inserisco dentro la lista degli ingredienti
				alimentiMangiati.add(Pair.fromJsonObject(alimenti.getJSONObject(i)));
			}
		} catch (JSONException e) {
		}

		// ora ritorniamo il pasto
		return new Pasto(data,categoria,alimentiMangiati);
	}
	
	
}
