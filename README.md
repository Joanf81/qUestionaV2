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

## Data Model
Data are organized in the datastore as follows, each user has an User type entity that will be the ancestor of the entities of type Questionnaire and Question. The question's ancestor is the User type entity, but the question entity has a attribute called 'tema'(topic), that allows us to known which questionnaire belongs the question. 

## Application deployment
To compile and deploy the application on a local server, you can import the project form eclipse, then compile and run it as a web application. When the application is launched, it can be accesed through a web navigator using the newt URL:  
`http://localhost:8888/`  

## Implementing on Google App Engine
To implement it in the GAE platform, it's necesary, before compiling the code, to edit the 'WEB-INF/lib/appengine-web.xml' file and add the GAE platform Application ID code between the 'application' xml tags as shown belong:  
`<?xml version="1.0" encoding="utf-8"?>`  
`<appengine-web-app xmlns="http://appengine.google.com/ns/1.0">`  
`<application><!-- YOUR APP ID OF GAE --></application>`  
`<version>1</version>`  

## Tested in...
The application has been correctly complied and runned on the next system:  
`Ubuntu Linux 14.04 LTS`  
`Eclipse Mars.2 Release (4.5.2) Java EE IDE for Web Developers.`  
`Java SDK 1.7`  
`App Engine API v1.9.34`  

The application's view has been corrtley tested using the next nevigator:  
`Mozilla Firefox 46.0 for Ubuntu`  

## New technologies added
This application uses all the technologies mentioned in [qUestionaV1](https://github.com/Joanf81/qUestionaV1 "qUestionaV1"), but aside, it introduces the next:  

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

## Author
Application completly developed by Joan Fernández Bornay.
