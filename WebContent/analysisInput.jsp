<%@ page import="java.util.*, pokemon.analysis.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String pokemon = request.getParameter("pokemon name");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pokemon Analysis</title>
</head>
<body>
	<%
		Analysis analysis = new Analysis();
		String pokeWeaknesses = analysis.printWeaknesses(pokemon);
	%>

	<%
		String delim = "[\n]";
		String[] weaknesses = pokeWeaknesses.split(delim);
		for (String weakness : weaknesses) {
	%>
	<%=weakness%>
	<br>

	<%
		}
	%>


</body>
</html>