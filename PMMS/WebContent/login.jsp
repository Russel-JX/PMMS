<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
<script>
function validate()
{
	var x = document.forms["myForm"]["ssoId"].value;
	var y = document.forms["myForm"]["password"].value;
	
    if ((x == null || x == "") && (y == null || y == ""))
    {
        alert("SSO ID & Password must be filled out");
        return false;
    }
    else if(x == null || x == "")
    {
    	{
            alert("SSO ID must be filled out");
            return false;
        }
    }
    else if(y == null || y == "")
    {
    	{
            alert("Password must be filled out");
            return false;
        }
    }
    	
}
</script>
</head>
<body>

<form:form name="myForm" method="post" modelAttribute="user" action="validate.do">

<h2 align="center">USER LOGIN PAGE<br></h2>
<center>
${message }
<table border="1" bordercolor="blue">
	<tr>
		<td>SSO ID : </td>
		<td><form:input path="ssoId"/></td>
	</tr>
	<tr>
		<td>Password : </td>
		<td><form:input type="password" path="password"/></td>
	</tr>
	<tr align="center">
		<td colspan="2" style="height: 48px; "><input type="submit" value="Login" ></td>
	</tr>
</table>
</center>
</form:form>

</body>
</html>



