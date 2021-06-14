package fr.eni.javaee.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.bll.SuiviRepasManager;
import fr.eni.javaee.bo.Repas;
import fr.eni.javaee.exception.BusinessException;

import java.io.IOException;
import java.util.List;


@WebServlet("/ServletVisualisationRepas")
public class ServletVisualisationRepas extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// S'il n'y a pas de problème dans le select, affiche la liste des repas
		try {
		List<Repas> listeRepas = new SuiviRepasManager().selectAll();
		request.setAttribute("listeRepas", listeRepas);
		// Sinon envoie la liste des codes d'erreur
		} catch (BusinessException be){
			be.printStackTrace();
			request.setAttribute("listeCodesErreur", be.getListeCodesErreur());
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/historique.jsp");
		rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
