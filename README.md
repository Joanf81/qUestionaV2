# qUestionaV2

## Introduction
qUestionaV2 is a improvement of [qUestionaV1](https://github.com/Joanf81/qUestionaV1 "qUestionaV1") that introduces a backend for managing a data base with the information of the questionnaires. The application is prepared to be implemented in the Google App Engine (GAE) cloud computing platform.

Now, when entering the application, it first will ask us about our Google login (if we are working on a local server, this login may not to be valid). Once we are logged in, we can work on the questionnaires on the same way we did on [qUestionaV1](https://github.com/Joanf81/qUestionaV1 "qUestionaV1"), but now, anay changes we make will be recorded in the database, and if we re-enter the application using the same login, the data of the questionnaires will be recovered.

## Application's Skeleton
### Backend
The application's backend is a web service compund of a combination of Java Servlets, whose function is to communicate the view with the database, allowing the data persistence. Each servlets represents an action that can be done on the questionnaires, like list questions, delete questionnaires, create new question, etc,.. When a user performs an action on the application's view, this calls the corresponding servlet, that is responsible for consulting or updating the database, depending on the action that the user performed.
For de exchange of data beetwen the backside and frontisde, Javascript Object Notation (JSON) has been used.

### Frontend
The application uses the same frontend as in the previous version, but now it introduces the Java Server Pages (JSP) tecnology, that allows the view to communicate with the backend. Also, it has been added a new page called 'welcome.jsp' that allows the user to login in the application, and where the user is redirected in case of not being logged.

### Data persistence engine
Since the application is designed to be implanted on the GAE platform, the data will be stored in the Google Cloud Datastore, a NoSQL squemaless database used by Google App Engine.

## Modelo de datos
Data are organized in the datastore as follows, each user has an User type entity that will be the ancestor of the 

En el datastore los datos estan organizados de la siguiente forma, cada usuario tiene una entidad de tipo Usuario que servirá de ancestro a las entidades de tipo Cuestionario y Pregunta. Las preguntas tendran como ancestro la entidad de tipo usuario, pero tendrán un atributo llamado tema para saber a que cuestionario pertenecen.

## Despligue de la aplicación
Para compilar y desplegar aplicación en un servidor local, puedes importar el proyecto desde eclipse, compilarlo y ejecutarlo como una aplicación de tipo Web Application. Una vez lanzada, la aplicación puede ser accedida a traves de nuestro navegar web usando la siguiente URL:  
`http://localhost:8888/`  

## Implantación en Google App Engine 
Para implantar la aplicación en la plataforma GAE, es necesario, antes de compilar el código, editar el fichero 'WEB-INF/lib/appengine-web.xml' y añadir el Application ID, proporcionado por la plataforma GAE, dentro de las etiquetas 'application', como se muestra a continuación:  
`<?xml version="1.0" encoding="utf-8"?>`  
`<appengine-web-app xmlns="http://appengine.google.com/ns/1.0">`  
`<application><!-- YOUR APP ID OF GAE --></application>`  
`<version>1</version>`  

## Probada en...
La aplicación ha sido correctamente compilada y probada en el siguiente sistema:  
`Ubuntu Linux 14.04 LTS`  
`Eclipse Mars.2 Release (4.5.2) Java EE IDE for Web Developers.`  
`Java SDK 1.7`  
`App Engine API v1.9.34`  

La interfaz de la aplicación ha sido testada correctamente con el siguiente navegador:  
`Mozilla Firefox 46.0 for Ubuntu`  

## Nuevas tecnologías añadidas
Esta aplicación usa todas las tecnologías mencionadas en [qUestionaV1](https://github.com/Joanf81/qUestionaV1 "qUestionaV1"), pero a parte, se añaden las siguinetes:

Java https://www.java.com/  
Java EE http://www.oracle.com/technetwork/java/javaee/overview/index.html  
Java Servlets http://www.oracle.com/technetwork/java/index-jsp-135475.html  
Java Server Pages (JSP) http://www.oracle.com/technetwork/java/javaee/jsp/index.html  
JavaScript Object Notation (JSON) http://www.w3schools.com/js/js_json_intro.asp  
  
Cloud computing https://en.wikipedia.org/wiki/Cloud_computing  
Platform as a Service (PaaS) https://en.wikipedia.org/wiki/Platform_as_a_service  
Google App Engine (GAE) https://cloud.google.com/appengine/  

NoSQL http://nosql-database.org/  
Datastore https://cloud.google.com/datastore/  
  
Eclipse IDE for Java EE Developers http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/mars2  

## Autor
Aplicación completamente desarrollada por Joan Fsernández Bornay.
