<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<% 
out.print(request.getSession().getAttribute("ssoId"));
%>
<br/><br/>

${ssoid }
<br/><br/>
${user1.ssoId}
<br/><br/>
${user1.password}
<br/><br/>
${user1.name}
<br/><br/>
${user1.role}
</body>
</html>