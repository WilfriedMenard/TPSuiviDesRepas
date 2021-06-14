package fr.eni.javaee.message;

import java.util.ResourceBundle;

public abstract class LecteurMessage {

	private static ResourceBundle rb;
	
	// On récupère les codes et messages de "messages_erreur.properties"
	static {
		try {
			rb = ResourceBundle.getBundle("fr.eni.javaee.message.messages_erreur");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Méthode afin que "ajout.jsp" obtient les messages d'erreur avec le code correspondant
	public static String getMessageErreur(int codeErreur) {
		String message = "";
		try {
			if(rb != null) {
				message = rb.getString(String.valueOf(codeErreur));
			} else {
				message = "Problème à la lecture du fichier contenant les messages";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "Erreur inconnue";
		}
		return message;
	}
}
