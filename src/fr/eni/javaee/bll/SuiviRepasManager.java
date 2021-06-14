package fr.eni.javaee.bll;

import fr.eni.javaee.dal.SuiviRepasDAO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.bo.Aliment;
import fr.eni.javaee.bo.Repas;
import fr.eni.javaee.dal.DAOFactory;
import fr.eni.javaee.dal.SuiviRepasJdbcImpl;
import fr.eni.javaee.exception.BusinessException;

public class SuiviRepasManager {
	private SuiviRepasDAO suiviRepasDao = DAOFactory.getSuiviRepasDAO();
	
	public List<Repas> selectAll() throws BusinessException {
		return suiviRepasDao.selectAll();
	}
	
	public Repas ajouterRepas(LocalDate date, LocalTime heure, List<String> listStringAliments) throws BusinessException {
		
		// Validation des données par le biais de BusinessException
		BusinessException be = new BusinessException();
		validationDate(date, be);
		validationHeure(heure, be);
		
		// S'il y a une erreur, lève l'exception
		if(be.hasErreurs()) { // hasErreurs() est une méthode de BusinessException : si la liste d'erreur est > 0 donc si elle comprend un message d'erreur
			throw be;
		}
		
		// transforme la liste de String en liste d'Aliments
		List<Aliment> listAliments = new ArrayList<>();
		for (String chaqueAliment : listStringAliments) {
			listAliments.add(new Aliment(chaqueAliment));
		}
		Repas nouveauRepas = new Repas(date, heure, listAliments);
		suiviRepasDao.ajoutRepas(nouveauRepas);
		return nouveauRepas;
	}
	
	public void validationDate(LocalDate date, BusinessException be) {
		if (date == null) {
			be.ajouterErreur(CodesErreurBLL.REPAS_DATE_OBLIGATOIRE);
		}
	}
	
	public void validationHeure(LocalTime heure, BusinessException be) {
		if (heure == null) {
			be.ajouterErreur(CodesErreurBLL.REPAS_HEURE_OBLIGATOIRE);
		}
	}
}
