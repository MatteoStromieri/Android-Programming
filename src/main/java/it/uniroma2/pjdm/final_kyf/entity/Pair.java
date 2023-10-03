package it.uniroma2.pjdm.final_kyf.entity;

import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONException;

public class Pair{
	public AlimentoAstratto alimento;
	public double quantita;
	
	public Pair(AlimentoAstratto alimento, double quantita){
		this.alimento = alimento;
		this.quantita = quantita;
	}
	
	public Pair(AlimentoAstratto alimento){
		this(alimento,0);
	}

	
	
	public AlimentoAstratto getAlimento() {
		return alimento;
	}

	public void setAlimento(AlimentoAstratto alimento) {
		this.alimento = alimento;
	}

	public double getQuantita() {
		return quantita;
	}

	public void setQuantita(double quantita) {
		this.quantita = quantita;
	}

	@Override
	public int hashCode() {
		return Objects.hash(alimento);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		return Objects.equals(alimento, other.alimento);
	}
	
	public String toJSONString() throws JSONException {
		return new JSONArray(this).toString();
	}
	
	
}