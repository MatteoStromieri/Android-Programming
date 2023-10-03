package it.uniroma2.pjdm.final_kyf.entity;

import java.sql.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class Richiesta {
	
	public Date data;
	public String descrizione;
	public Utente mittente;
	public Nutrizionista destinatario;

	public Richiesta(Date data, String descrizione, Utente mittente, Nutrizionista destinatario){
		this.data = data;
		this.descrizione = descrizione;
		this.mittente = mittente;
		this.destinatario = destinatario;
	}

	public Date getData(){
		return this.data;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public Utente getMittente() {
		return mittente;
	}

	public Nutrizionista getDestinatario() {
		return destinatario;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public void setMittente(Utente mittente) {
		this.mittente = mittente;
	}

	public void setDestinatario(Nutrizionista destinatario) {
		this.destinatario = destinatario;
	}

	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;
		if (!super.equals(object)) return false;
		Richiesta richiesta = (Richiesta) object;
		return java.util.Objects.equals(data, richiesta.data) && java.util.Objects.equals(descrizione, richiesta.descrizione) && java.util.Objects.equals(mittente, richiesta.mittente) && java.util.Objects.equals(destinatario, richiesta.destinatario);
	}

	public int hashCode() {
		return java.util.Objects.hash(super.hashCode(), data, descrizione, mittente, destinatario);
	}

	public String toJSONString() throws JSONException {
		return new JSONObject(this).toString();
	}
}
