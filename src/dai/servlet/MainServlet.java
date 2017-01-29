package dai.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.users.UserService;

import com.google.appengine.api.users.UserServiceFactory;

public class MainServlet extends HttpServlet {
	
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws IOException, ServletException {
    	
      UserService userService = UserServiceFactory.getUserService();
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

      
      String thisURL = req.getRequestURI();
      resp.setContentType("text/html");
      resp.setCharacterEncoding("UTF-8");
      
      //Si estamos logueados..
      if (req.getUserPrincipal() != null) {
    	  
    	//Obtiene el usuario con el que te has identificado
        String userName = req.getUserPrincipal().getName();
        
        //Prepara una consulta para saber si el usuario tiene ya una entidad en la db con su nombre de usuario como clave:
        
    	Key userKey = KeyFactory.createKey("Usuario", userName);
    	
        Filter keyFilter = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, userKey);
        Query consulta = new Query("Usuario").setFilter(keyFilter);
        List<Entity> usuarios = datastore.prepare(consulta).asList(FetchOptions.Builder.withDefaults());

        if (usuarios.size() == 0) {
        	
        	Entity nuevo_usuario = new Entity("Usuario", userName);
        	datastore.put(nuevo_usuario);
        	
        	System.out.println("Registered Usuario entity with key '" + userName + "' in DataStore");
        } else System.out.println("Usuario entity with key '" + userName + "' is already registered in DataStore");

        //Preparar atributos para la vista main.jsp
        req.setAttribute("logout", userService.createLogoutURL(thisURL));
        req.setAttribute("usuario", userName);
        
        //Redireccionar a la vista main.jsp
        RequestDispatcher view = req.getRequestDispatcher("/main.jsp");
        view.forward(req, resp);
        
        
      //Si no estamos logueados..
      } else {
    	
    	System.out.println("Usuario no idenficado, redireccionado pagina de bienvenida.");  
    	  
    	req.setAttribute("login", userService.createLoginURL(thisURL));
        RequestDispatcher view = req.getRequestDispatcher("/welcome.jsp");
        
        view.forward(req, resp);
        
      }
    }
    
  }