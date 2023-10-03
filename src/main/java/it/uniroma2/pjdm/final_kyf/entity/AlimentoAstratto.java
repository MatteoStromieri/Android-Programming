package it.uniroma2.pjdm.final_kyf.entity;

import org.json.JSONObject;

public abstract class AlimentoAstratto {
	
	public int id;
	public String nome;
	public String descrizione;
	public Nutrizionista creatore;
	
	public AlimentoAstratto(int id, String nome, String descrizione, Nutrizionista creatore) {
		super();
		this.id = id;
		this.nome = nome;
		this.descrizione = descrizione;
		this.creatore = creatore;
	}
	
	public AlimentoAstratto(int id, String nome, String descrizione) {
		this(id,nome,descrizione,null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Nutrizionista getCreatore() {
		return creatore;
	}

	public void setCreatore(Nutrizionista creatore) {
		this.creatore = creatore;
	}
	
	public String toJSONString() {
		return new JSONObject(this).toString();
	}
	
}
