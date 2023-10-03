package it.uniroma2.pjdm.final_kyf.dao.alimento;

import java.util.ArrayList;

import it.uniroma2.pjdm.final_kyf.entity.Alimento;

public interface AlimentoDAO {
	
	public Alimento getAlimentoById(int id);
	
	public ArrayList<Alimento> getAlimentiByName(String name);
	
	public int addAlimento(Alimento alimento);
	
	public boolean deleteAlimento(int id);
	
	public void closeConnection();
}
