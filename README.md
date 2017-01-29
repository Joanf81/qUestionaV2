# qUestionaV2

## Introducción
qUestionV2 es una mejora de la applicación [qUestionaV1](https://github.com/Joanf81/qUestionaV1 "qUestionaV1") donde se introduce un backend que gestiona una base de datos con la información de los cuestionarios. La aplicación está preparada para implantarse en la plataforma de computación en la nube Google App Engine (GAE).  

Ahora, al entrar en la plicación, primero nos pedirá que nos logueemos con un usuario de Google (si estamos trabajando con un servidor local, este usuario no tiene porque ser valido), una vez logueados podremos trabajar con los cuestionarios de la misma forma que en [qUestionaV1](https://github.com/Joanf81/qUestionaV1 "qUestionaV1"), solo que ahora, cualquier cambio que hagamos quedara registrado en la base de datos, y cuando volvamos a acceder con el mismo usuario, recuperaremos los cuestionarios almacenados.  

## Estructura de la aplicación
### Backend
El backend de la aplicación es un servicio web compuesto de varios Java Servlets, cuya función es comunicar la vista con la base de datos, y así permitir la persistencia de datos. Cada Servlet representa una acción que se puede realizar sobre los cuestionarios, como listar preguntas, borrar cuestionario, nueva pregunta, etc,.. Cuando un usuario realiza una acción sobre la vista de la aplicación, esta llama al servlet correspondiente, que se encarga de consultar o modificar la base de datos, dependiendo de la acción que ha realizado el usuario.  
Para el intercambio de datos entre el backend y el fronted se ha usado el lenguaje JavaScript Object Notation (JSON).

### Frontend
El frontend es el mismo que el de la versión anterior, solo que ahora se ha introducido la tecnología Java Server Pages (JSP) para permitir a las vistas comunicarse con el backend. Además, se ha añadido una nueva pagina 'welcome.jsp' que permite loguarse en la aplicación, y donde el usuario es redirigido en caso de no estar logueado.

### Motor de persistencia de datos
Dado que la aplicación está diseñada para implantarse en la plataforma GAE, los datos se almacenan en Google Cloud Datastore, la base de datos de tipo NoSQL sin esquemas de Google App Engine.

## Modelo de datos
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
