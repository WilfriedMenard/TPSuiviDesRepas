package fr.eni.javaee.servlets;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.bll.SuiviRepasManager;
import fr.eni.javaee.bo.Aliment;
import fr.eni.javaee.exception.BusinessException;


@WebServlet("/ServletAjoutRepas")
public class ServletAjoutRepas extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ajout.jsp");
		rd.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Permet l'encodage UTF-8 dans la BDD (prend en compte les accents par exemple)
		request.setCharacterEncoding("UTF-8");
		
		// Récupération des données du formulaire de ajout.jsp et prise en compte des exceptions si nécesssaire
		String date = request.getParameter("date");
		LocalDate dateConverti = null;
		
		String heure = request.getParameter("heure");
		LocalTime heureConverti = null;
		
		String repas = request.getParameter("repas");
		List<String> listStringAliments = new ArrayList<>();
		
		// Séparation de chaque aliments et transformations de repas (String) en liste d'aliments
		// Le split permet de séparer chaque mot après chaque virgule
		listStringAliments = Arrays.asList(repas.trim().split(", "));
		for (String chaqueAliment : listStringAliments) {
			System.out.println(chaqueAliment);
		}
		
		BusinessException be = new BusinessException();
		
		// Conversion d'un string en date
		try {
		dateConverti = LocalDate.parse(date);
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			be.ajouterErreur(CodesErreurServlet.FORMAT_DATE_INCORRECT);
		}
		
		// Conversion d'un string en heure
		try {
		heureConverti = LocalTime.parse(heure);
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			be.ajouterErreur(CodesErreurServlet.FORMAT_HEURE_INCORRECT);
		}
		
		// S'il y a une erreur dans la liste d'erreur, on retourne sur la page ajout.jsp
		if(be.hasErreurs()) {
			request.setAttribute("listeCodesErreurs", be.getListeCodesErreur());
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ajout.jsp");
			rd.forward(request, response);	
		} else {	
		// Instanciation du Manager pour faire communiquer l'IHM au Manager avec les méthodes dont on a besoin
		try {
			SuiviRepasManager instanceManager = new SuiviRepasManager();
			instanceManager.ajouterRepas(dateConverti, heureConverti, listStringAliments);
			// Si la validation est ok on va dans l'historique
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/historique.jsp");
			rd.forward(request, response);
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("listeCodesErreurs", e.getListeCodesErreur());
			// Sinon s'il y a un problème dans l'insertion on reste sur ajout.jsp
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ajout.jsp");
			rd.forward(request, response);	
		}
		}
	}
}
