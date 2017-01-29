# qUestionaV2

## Introducción
qUestionV2 es una mejora de la applicación [qUestionaV1](https://github.com/Joanf81/qUestionaV1 "qUestionaV1") donde se introduce un backend que gestiona una base de datos con la información de los cuestionarios. La aplicación está preparada para implantarse en la plataforma de computación en la nube Google App Engine (GAE).  

Ahora, al entrar en la plicación, primero nos pedirá que nos logueemos con un usuario de Google (si estamos trabajando con un servidor local, este usuario no tiene porque ser valido), una vez logueados podremos trabajar con los cuestionarios de la misma forma que en [qUestionaV1](https://github.com/Joanf81/qUestionaV1 "qUestionaV1"), solo que ahora, cualquier cambio que hagamos quedara registrado en la base de datos, y cuando volvamos a acceder con el mismo usuario, recuperaremos los cuestionarios almacenados.  

## Estructura de la aplicación
### Backend
El backend de la aplicación es un servicio web compuesto de varios Java Servlets, cuya función es comunicar la vista con la base de datos, y así permitir la persistencia de datos. Cada Servlet representa una acción que se puede realizar sobre los cuestionarios, como listar preguntas, borrar cuestionario, nueva pregunta, etc,.. Cuando un usuario realiza una acción sobre la vista de la aplicación, esta llama al servlet correspondiente, que se encarga de consultar o modificar la base de datos, dependiendo de la acción que ha realizado el usuario.

## Frontend
El frontend es el mismo que el de la versión anterior, solo que ahora se ha introducido la tecnología Java Server Pages (JSP) para permitir a las vistas comunicarse con el backend. Además, se ha añadido una nueva pagina 'welcome.jsp' que permite loguarse en la aplicación, y donde el usuario es redirigido en caso de no estar logueado.

## Motor de persistencia de datos
Dado que la aplicación está diseñada para implantarse en la plataforma GAE, los datos se almacenan en el DataStore, la base de datos de tipo NoSQL de Google App Engine


GAE
Java Servltes
NoSQL
Clous computing
platform as a service
