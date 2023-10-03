package it.uniroma2.pjdm.final_kyf.entity;

import org.json.JSONObject;

public class Alimento extends AlimentoAstratto {
	
	public double carboidrati;
	public double proteine;
	public double grassi;
	
	public Alimento(int id, String nome, String descrizione, Nutrizionista creatore, double carboidrati, double proteine,
			double grassi) {
		super(id, nome, descrizione, creatore);
		this.carboidrati = carboidrati;
		this.proteine = proteine;
		this.grassi = grassi;
	}

	public double getCarboidrati() {
		return carboidrati;
	}

	public void setCarboidrati(double carboidrati) {
		this.carboidrati = carboidrati;
	}

	public double getProteine() {
		return proteine;
	}

	public void setProteine(double proteine) {
		this.proteine = proteine;
	}

	public double getGrassi() {
		return grassi;
	}

	public void setGrassi(double grassi) {
		this.grassi = grassi;
	}
	
	public String toJSONString() {
		return new JSONObject(this).toString();
	}
	
}
