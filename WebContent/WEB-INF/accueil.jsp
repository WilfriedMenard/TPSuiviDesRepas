<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Accueil Suivi Repas</title>
</head>
<body>
	<h1>ACCUEIL</h1>
	
	<form action="<%=request.getContextPath() %>/ServletAjoutRepas" method="GET">
		<button>Ajouter un nouveau repas</button>
	</form>
	<br>
	<form action="<%=request.getContextPath() %>/ServletVisualisationRepas" method="GET">
		<button>Visualiser les repas</button>
	</form>

</body>
</html>