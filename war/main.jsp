<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%
	String url_logout = (String)request.getAttribute("logout");
	String usuario = (String)request.getAttribute("usuario");
%>
    
  <head>
    <meta charset="utf-8">
    <title>qÜestiona</title>
    <script type="text/javascript" src="js/script.js"></script>
    <link rel="stylesheet" type="text/css" href="./css/normal.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="components/webcomponentsjs/webcomponents.js"></script>
    <link rel="import" href="miscomponentes/encabezado-cuestionario.html" />
    <link href='https://fonts.googleapis.com/css?family=Droid+Serif' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Ubuntu' rel='stylesheet' type='text/css'>
  </head>
      
  <body>
  	
  	  <header>
        <h1>qÜestiona</h1>
        <p>qÜestiona es una aplicación web que permite gestionar cuestionarios sobre un determinado tema en el que las posibles respuestas son verdadero o falso. La aplicación se desarrolla como parte de las prácticas de la asignatura Desarrollo de Aplicaciones en Internet del Grado de Ingeniería Informática de la Universitat d'Alacant.</p>
        <nav>
          <ul>

          </ul>
        </nav>
      </header>	 

      <main>

          <div class="formulario" id="nuevoCuestionario">
            <ul>
              <li>
                <label>Tema del cuestionario:</label>
                <input type="text" name="tema" autofocus>
              </li>
              <li>
                <input type="button" name="crea" value="Crear nuevo cuestionario">
              </li>
            </ul>
          </div>

      </main>

      <footer>
          <span id="npombre">
                Joan Fernández Bornay
          </span>
          -
          <span id="dni">
                48726966V
          </span>
          <span>
          | Práctica 3 de Desarrollo de Aplicaciones en Internet | Universitat d'Alacant
          </span>
          <span id="usuario">
          | <%=usuario%>
          </span>
          <span id="logout">
          | <a href="<%=url_logout%>">Salir</a>
          </span><span id="borrar">
          | <a href="">Borrar Datos</a>
          </span>
          
  	  </footer>

  </body>
</html>