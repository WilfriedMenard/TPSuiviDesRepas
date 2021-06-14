package fr.eni.javaee.bo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Repas {
	private int idRepas; 
	private LocalDate date;
	private LocalTime heure;
	private List<Aliment> listAlimentsRepas;
		
	public Repas() {
		listAlimentsRepas = new ArrayList<>();
	}
	
	public Repas(LocalDate date, LocalTime heure, List<Aliment> listAlimentsRepas) {
		this();
		this.date = date;
		this.heure = heure;
		this.listAlimentsRepas = listAlimentsRepas;
	}
	
	public Repas(int idRepas, LocalDate date, LocalTime heure) {
		this();
		this.idRepas = idRepas;
		this.date = date;
		this.heure = heure;
	}
	
	public int getIdRepas() {
		return idRepas;
	}
	
	public void setIdRepas(int idRepas) {
		this.idRepas = idRepas ;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public LocalTime getHeure() {
		return heure;
	}
	
	public void setHeure(LocalTime heure) {
		this.heure = heure;
	}
	
	public List<Aliment> getListAlimentsRepas() {
		return listAlimentsRepas;
	}

	public void setAliments(List<Aliment> listAlimentsRepas) {
		this.listAlimentsRepas = listAlimentsRepas;
	}
	
	public void ajouterAliment(Aliment aliment) {
		listAlimentsRepas.add(aliment);
	}

	@Override
	public String toString() {
		return "Repas [idRepas=" + idRepas + ", date=" + date + ", heure=" + heure + ", listAlimentsRepas="
				+ listAlimentsRepas + "]";
	}
	
	
	
}
