package it.uniroma2.pjdm.final_kyf.entity;

import java.sql.Date;
import java.util.ArrayList;

import org.json.JSONObject;

public class Utente {
	
	public String email;
	public String nome;
	public String cognome;
	public String password;
	public Date dataDiNascita;
	public Nutrizionista nutrizionista;
	public ArrayList<Pasto> diario;
	
	public Utente(String email, String nome, String cognome, String password, Date dataDiNascita, Nutrizionista nutrizionista, ArrayList<Pasto> diario) {
		super();
		this.email = email;
		this.nome = nome;
		this.cognome = cognome;
		this.password = password;
		this.dataDiNascita = dataDiNascita;
		this.nutrizionista = nutrizionista;
		this.diario = diario;
	}
	
	public Utente(String email, String nome, String cognome, String password, Date dataDiNascita) {
		this(email, nome, cognome, password, dataDiNascita, null, new ArrayList<Pasto>());
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Nutrizionista getNutrizionista() {
		return nutrizionista;
	}

	public void setNutrizionista(Nutrizionista nutrizionista) {
		this.nutrizionista = nutrizionista;
	}

	public ArrayList<Pasto> getDiario() {
		return diario;
	}

	public void setDiario(ArrayList<Pasto> diario) {
		this.diario = diario;
	}

	
	public String toJSONString() {
		return new JSONObject(this).toString();
	}
	
	
}
