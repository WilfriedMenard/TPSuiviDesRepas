package fr.eni.javaee.dal;

import fr.eni.javaee.bo.Repas;
import fr.eni.javaee.exception.BusinessException;

import java.util.List;

public interface SuiviRepasDAO {
	public Repas ajoutRepas(Repas repas) throws BusinessException;
	
	public List<Repas> selectAll() throws BusinessException; 
}
