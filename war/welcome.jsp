<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  	String url_login = (String)request.getAttribute("login");
%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Bienvenido a qÜestiona</title>

    <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/stylish-portfolio.css" rel="stylesheet">
    <link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">

</head>

<body>

    <header id="top" class="header">
        <div class="text-vertical-center">
            <h1>qÜestiona </h1>
            <h3>Crea cuestionarios en segundos</h3>
            <br> 
            <a href="<%=url_login%>" class="btn btn-dark btn-lg">Accede con tu cuenta de Google</a>
        
    </header>

    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</body>

</html>