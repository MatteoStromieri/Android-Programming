package it.uniroma2.pjdm.androidstudio.kyf.entity;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Alimento extends AlimentoAstratto {

	private final static String TAG = "AlimentoComposto";
	public double carboidrati;
	public double proteine;
	public double grassi;
	
	public Alimento(int id, String nome, String descrizione, String creatore, double carboidrati, double proteine,
			double grassi) {
		super(id, nome, descrizione, creatore,"alimento");
		this.carboidrati = carboidrati;
		this.proteine = proteine;
		this.grassi = grassi;
	}
	
	public Alimento( String nome, String descrizione, String creatore, double carboidrati, double proteine,
			double grassi) {
		super(0, nome, descrizione, creatore,"alimento");
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

	public static Alimento fromJsonObject(JSONObject jsonObject){
		int id;
		try {
			id = jsonObject.getInt("id");
		} catch (JSONException e) {
			id = -1;
		}
		String nome;
		try {
			nome=jsonObject.getString("nome");
		} catch (JSONException e) {
			Log.d(TAG, "fromJsonObject: nome assente");
			throw new RuntimeException(e);
		}
		String descrizione;
		try {
			descrizione = jsonObject.getString("descrizione");
		} catch (JSONException e) {
			descrizione = "";
		}
		String creatore;
		try {
			creatore=jsonObject.getString("creatore");
		} catch (JSONException e) {
			creatore = "";
		}

		Double carboidrati;
		try {
			carboidrati= jsonObject.getDouble("carboidrati");
		} catch (JSONException e) {
			Log.d(TAG, "fromJsonObject: carboidrati assenti");
			throw new RuntimeException(e);
		}

		Double proteine;
		try {
			proteine= jsonObject.getDouble("proteine");
		} catch (JSONException e) {
			Log.d(TAG, "fromJsonObject: proteine assenti");
			throw new RuntimeException(e);
		}

		Double grassi;
		try {
			grassi= jsonObject.getDouble("grassi");
		} catch (JSONException e) {
			Log.d(TAG, "fromJsonObject: grassi assenti");
			throw new RuntimeException(e);
		}
		return new Alimento(id,nome,descrizione,creatore,carboidrati,proteine,grassi);
	}

	
}
