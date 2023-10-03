package it.uniroma2.pjdm.final_kyf.dao.alimentoComposto;

import java.util.ArrayList;

public interface AlimentoDAO {
	
	public AlimentoComposto getAlimentoCompostoById(int id);
	
	public ArrayList<AlimentoComposto> getAlimentiByName(String name);
	
	public int addAlimento(Alimento alimento);
	
	public boolean deleteAlimento(int id);
	
	public void closeConnection();
}

