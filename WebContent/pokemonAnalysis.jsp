<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pokemon Analysis</title>
</head>
<body>
	<h3>Submit a pokemon to see its weaknesses</h3>
	
	<form name="frm" method="get" action="analysisInput.jsp">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="18%">&nbsp;</td>
				<td width="82%">&nbsp;</td>
			</tr>
			<tr>
				<td>Pokemon Name</td>
				<td><input type="text" name="pokemon name"></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" name="submit" value="Submit"></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</form>
</body>
</html>