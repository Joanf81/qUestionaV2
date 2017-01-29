package dai.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class BorraDatos extends HttpServlet {
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws IOException, ServletException {
    	
   
    	JSONObject respuesta_json = new JSONObject();
    	
    	try {
	
	    	// Obtener el identificador del usuario
    		String userName = req.getUserPrincipal().getName();
	    	Key userKey = KeyFactory.createKey("Usuario", userName);
	    		
	    	// Preparar una consulta para obtener todos los cuestionatios del usuario
	    	//Filter temaFilter = new FilterPredicate("tema", FilterOperator.EQUAL, tema_cuestionario);
	        Query consulta_cuest = new Query("Cuestionario").setAncestor(userKey);
	        List<Entity> cuestionarios = datastore.prepare(consulta_cuest).asList(FetchOptions.Builder.withDefaults());
	        
	        for (Entity cuestionario: cuestionarios) {
	        	
	        	Filter temaFilter = new FilterPredicate("tema", FilterOperator.EQUAL, cuestionario.getProperty("tema"));
		        Query consulta_preg = new Query("Pregunta").setFilter(temaFilter).setAncestor(userKey);
		        List<Entity> preguntas = datastore.prepare(consulta_preg).asList(FetchOptions.Builder.withDefaults());
		        
		        for (Entity pregunta: preguntas) {
		        	
		        	datastore.delete(pregunta.getKey());
		        }
		        
		        datastore.delete(cuestionario.getKey());
	        }
	    		
	
		    // Responder a la peticion ---> Cuestionario eliminado CORRECTAMENTE
		    JSONArray array = new JSONArray();
			respuesta_json.put("result", array);	
					

		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		
		System.out.println("Servlet BorraDatos: Recibido <--");
		System.out.println("Servlet BorraDatos: Respuesta --> " + respuesta_json.toString());
		
		// Preparar y escribir respuesta
		resp.setContentType("application/json");
		resp.getWriter().write(respuesta_json.toString());
    }
    
}