package it.uniroma2.pjdm.final_kyf.entity;

import java.sql.Date;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class Pasto {

	public Date data;
	public String categoria;
	public ArrayList<Pair> alimenti;
	
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
	
	
	public String toJSONString() throws JSONException {
		return new JSONObject(this).toString();
	}
	
	
}
