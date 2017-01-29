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
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class ListaPreguntas extends HttpServlet {
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws IOException, ServletException {
    	
    	// Obtener el parametro tema de la peticion
    	String tema = req.getParameter("tema");
   
    	JSONObject respuesta_json = new JSONObject();
    	
    	// Si no se especifica correctamente el paramentro tema..
    	if (tema == null || tema == "") {
    		
    		JSONObject message = new JSONObject();
    		try {
				message.put("message", "se esperaba el parámetro tema");
				respuesta_json.put("error", message);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		
    	// Si todos los parametros se han especificado correctamente..
    	} else {
    	
	    	// Obtener el identificador del usuario
	    	String userName = req.getUserPrincipal().getName();
	    	Key userKey = KeyFactory.createKey("Usuario", userName);
	    	
	    	Filter temaFilter = new FilterPredicate("tema", FilterOperator.EQUAL, tema); // Filtro para obtener las preguntas con el tema pasado por parametros
	    	Query consulta = new Query("Pregunta").setFilter(temaFilter).setAncestor(userKey).addSort("pregunta", SortDirection.ASCENDING);
	
	        List<Entity> preguntas = datastore.prepare(consulta).asList(FetchOptions.Builder.withDefaults());
	        JSONArray array = new JSONArray();
	    		
	        // Rellenar un array json para usar en la respuesta
	        for (Entity pregunta : preguntas) {
	        	
	        	JSONObject pregunta_json = new JSONObject();
	        	try {
					pregunta_json.put("pregunta", pregunta.getProperty("pregunta"));
					pregunta_json.put("respuesta", pregunta.getProperty("respuesta"));
		        	pregunta_json.put("tema", pregunta.getProperty("tema"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	array.put(pregunta_json); 	
	        }
	        
	        // Preparar la respuesta con el listado de temas
			try {
				respuesta_json.put("result", array);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		
		System.out.println("Servlet ListaPreguntas: Recibido <-- tema= " + tema);
		System.out.println("Servlet ListaPreguntas: Respuesta --> " + respuesta_json.toString());
		
		// Preparar y escribir respuesta
		resp.setContentType("application/json");
		resp.getWriter().write(respuesta_json.toString());
    }
    
}

