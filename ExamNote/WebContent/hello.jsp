<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html">
<html ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/angular.min.js"></script>
<title>Login Example</title>
</head>
<body>
	<Form action="LoginServlet" method="post">
		Username: <input type="text" name="username" ng-model="username"/><h3>Hello {{username}}!</h3><br/>
		Password: <input type="password" name="password"/><br/>
		<input type="submit" value="Login"/>
	</Form>
	
</body>
</html>