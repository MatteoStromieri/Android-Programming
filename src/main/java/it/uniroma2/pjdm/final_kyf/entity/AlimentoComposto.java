package it.uniroma2.pjdm.final_kyf.entity;

import java.util.ArrayList;

import org.json.JSONObject;

public class AlimentoComposto extends AlimentoAstratto {
	
	public ArrayList<Pair> alimenti;
	
	public AlimentoComposto(int id, String nome, String descrizione, Nutrizionista creatore, ArrayList<Pair> alimenti) {
		super(id, nome, descrizione, creatore);
		this.alimenti = alimenti;
	}

	public ArrayList<Pair> getAlimenti() {
		return alimenti;
	}

	public void setAlimenti(ArrayList<Pair> alimenti) {
		this.alimenti = alimenti;
	}

	public String toJSONString() {
		return new JSONObject(this).toString();
	}
	

}
