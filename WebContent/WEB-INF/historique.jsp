<%@page import="fr.eni.javaee.message.LecteurMessage"%>
<%@page import="fr.eni.javaee.bo.Aliment"%>
<%@page import="fr.eni.javaee.bo.Repas"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Historique des ajouts</title>
</head>
<body>
	<h1>HISTORIQUE</h1>
		
	<%
		List<Integer> erreurs = (List<Integer>)request.getAttribute("listeCodesErreur");
		if(erreurs != null) {
	%>
	<h2 style="color:red;">Une erreur est survenue !</h2>
	<%
			for(int code : erreurs) {
	%>
		<p><%=LecteurMessage.getMessageErreur(code) %>
	<%
			}
		} else {
	%>
	
	
	<table align="center">
		<thead>
			<tr>
				<td>Date</td>
				<td>Heure</td>
				<td>Aliments</td>
			</tr>
		</thead>
		
		<%
			List<Repas> listeRepas = (List<Repas>)request.getAttribute("listeRepas");
			if(listeRepas == null || listeRepas.isEmpty()) {
		%>
		<p>Il n'y a pas de repas</p>
		<%
			} else {
		%>
		<tbody>
			<%
				for(Repas chaqueRepas : listeRepas) {
			%>
			<tr>
				<td><%=chaqueRepas.getDate() %></td>
				<td><%=chaqueRepas.getHeure() %></td>
				<td>
					<ul>
					<%
						for(Aliment chaqueAliment : chaqueRepas.getListAlimentsRepas()) {
					%>
						<li><%=chaqueAliment.getNomAliment() %></li>
					<%
						}
					%>
					</ul>
				</td>
			</tr>
			<%
				}
			%>
		</tbody>
		<%
			}
		%>
	</table>
	
	<%
		}
	%>
	
	<form action="<%=request.getContextPath() %>/ServletAjoutRepas" method="GET">
		<button align="center">Ajouter un nouveau repas</button>
	</form>
	<br>
	<form action="<%=request.getContextPath() %>/accueil">
		<button align="center" >Annuler</button>
	</form>
	
</body>
</html>