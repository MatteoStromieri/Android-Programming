package it.uniroma2.pjdm.androidstudio.kyf.entity;

import android.util.Log;

import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AlimentoAstratto {

	private static final String TAG = AlimentoAstratto.class.getSimpleName();
	public int id;
	public String nome;
	public String descrizione;
	public String creatore;
	public String tipologia;
	
	public AlimentoAstratto(int id, String nome, String descrizione, String creatore, String tipologia) {
		super();
		this.id = id;
		this.nome = nome;
		this.descrizione = descrizione;
		this.creatore = creatore;
		this.tipologia = tipologia;
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

	public String getCreatore() {
		return creatore;
	}

	@Override
	public int hashCode() {
		return Objects.hash(creatore, descrizione, id, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlimentoAstratto other = (AlimentoAstratto) obj;
		return Objects.equals(creatore, other.creatore) && Objects.equals(descrizione, other.descrizione)
				&& id == other.id && Objects.equals(nome, other.nome);
	}

	public static AlimentoAstratto fromJsonObject(JSONObject jsonObjectAlimento) {
		String tipologiaAlimento;
		try {
			tipologiaAlimento = jsonObjectAlimento.getString("tipologia");
		} catch (JSONException e) {
			Log.d(TAG, "fromJsonObject: tipologia non presente in alimento astratto");
			throw new RuntimeException(e);
		}

		if(tipologiaAlimento.equals("alimento")){
			return Alimento.fromJsonObject(jsonObjectAlimento);
		} else if (tipologiaAlimento.equals("alimentoComposto")){
			return AlimentoComposto.fromJsonObject(jsonObjectAlimento);
		}

		Log.d(TAG, "fromJsonObject: tipologia presente ma non valida in alimento astratto");
		throw new RuntimeException();
	}
	
	public String getTipologia() {
		return tipologia;
	}
}
