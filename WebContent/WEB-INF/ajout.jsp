<%@page import="java.util.List"%>
<%@page import="fr.eni.javaee.message.LecteurMessage"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ajout d'un repas</title>
</head>
<body>
	<h1>AJOUT D'UN REPAS</h1>
	
	<%
		List<Integer> erreurs = (List<Integer>)request.getAttribute("listeCodesErreur");
		if(erreurs != null) {
	%>
	<h2 style="color:red;">Une erreur est survenue !</h2>
	<%
			for(int code : erreurs) {
	%>
		<p><%=LecteurMessage.getMessageErreur(code) %></p>
	<%
			}
		}
	%>
		
	<form action="<%=request.getContextPath() %>/ServletAjoutRepas" method="POST">
		<label for="date">Date : </label>
        <input type="date" name="date" placeholder="Date du repas ?" required>
        <br><br>
        <label for="heure">Heure : </label>
        <input type="time" name="heure" placeholder="L'heure du repas ?" required>
		<br><br>
		<label for="repas">Repas : </label>
        <textarea name="repas" placeholder="Que voulez-vous mangez ?" rows="5" cols="35" required></textarea>
		<br><br>			
		<input type="submit" value="Valider">
		<br><br>
	</form>
	<form action="<%=request.getContextPath() %>/accueil">
		<button>Annuler</button>
	</form>
</body>
</html>