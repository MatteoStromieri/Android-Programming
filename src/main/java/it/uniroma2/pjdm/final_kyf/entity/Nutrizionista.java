package it.uniroma2.pjdm.final_kyf.entity;

import java.sql.Date;
import java.util.ArrayList;

import org.json.JSONObject;

public class Nutrizionista {
	
	public String email;
	public String nome;
	public String cognome;
	public String password;
	public Date dataDiNascita;
	public ArrayList<Richiesta> richiesteAttive;
	public ArrayList<Utente> pazienti;
	
	public Nutrizionista(String email, String nome, String cognome, String password, Date dataDiNascita, ArrayList<Richiesta> richiesteAttive,  ArrayList<Utente> pazienti) {
		super();
		this.email = email;
		this.nome = nome;
		this.cognome = cognome;
		this.password = password;
		this.dataDiNascita = dataDiNascita;
		this.richiesteAttive = richiesteAttive;
		this.pazienti = pazienti;
	}
	
	public Nutrizionista(String email, String nome, String cognome, String password, Date dataDiNascita) {
		this(email,nome,cognome,password,dataDiNascita,new ArrayList<Richiesta>(),new ArrayList<Utente>());
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

	public ArrayList<Richiesta> getRichiesteAttive() {
		return richiesteAttive;
	}

	public void setRichiesteAttive(ArrayList<Richiesta> richiesteAttive) {
		this.richiesteAttive = richiesteAttive;
	}

	public ArrayList<Utente> getPazienti() {
		return pazienti;
	}
	
	public String toJSONString() {
		return new JSONObject(this).toString();
	}
}
