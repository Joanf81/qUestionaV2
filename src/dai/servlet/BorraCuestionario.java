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

public class BorraCuestionario extends HttpServlet {
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws IOException, ServletException {
    	
    	// Obtenemos el tema del cuestionario a partir de la peticion
    	String tema_cuestionario = req.getParameter("tema");
   
    	JSONObject respuesta_json = new JSONObject();
    	
    	try {
    	
    		// Si el tema del cuestionario se ha especificado correctamente..
	    	if (tema_cuestionario != null && tema_cuestionario != "") {
	    		
	    		// Obtener el identificador del usuario
	    		String userName = req.getUserPrincipal().getName();
	    	    Key userKey = KeyFactory.createKey("Usuario", userName);
	    		
	    	    // Preparar una consulta para obtener todos los cuestionatios con tema == tema_cuestionario y ancestro con tu nombre de usuario como clave
	    		Filter temaFilter = new FilterPredicate("tema", FilterOperator.EQUAL, tema_cuestionario);
	            Query consulta = new Query("Cuestionario").setAncestor(userKey).setFilter(temaFilter);
	            List<Entity> cuestionarios = datastore.prepare(consulta).asList(FetchOptions.Builder.withDefaults());
	    		
	            // Si el usuario tiene un cuestionario con el tema indicado..	
	            if (cuestionarios.size() >= 1) {
	            	
		    		// Eliminar cuestionario del datastore
		    		datastore.delete(cuestionarios.get(0).getKey());
		    		
		    		// Responder a la peticion ---> Cuestionario eliminado CORRECTAMENTE
		    		JSONArray array = new JSONArray();
					respuesta_json.put("result", array);	
					
				// Si el usuario no tenia un cuestionario con el tema especificado..
	            } else {
	            	
	            	JSONObject message = new JSONObject();
	            	String message_error = "el tema " + tema_cuestionario + " no existe en la base de datos";
	            	message.put("message", message_error);
	            	respuesta_json.put("error", message);    	
	            }
	    		
	    	// Si no se ha especificado un tema para el cuestionario ---> Mendaje de error
	    	} else {
	    		
	    		JSONObject message = new JSONObject();
	    		message.put("message", "se esperaba el parámetro tema");
	    		respuesta_json.put("error", message);
	    		 
	    	}
    	
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		
		System.out.println("Servlet BorraCuestionario: Recibido <-- tema="+ tema_cuestionario);
		System.out.println("Servlet BorraCuestionario: Respuesta --> " + respuesta_json.toString());
		
		// Preparar y escribir respuesta
		resp.setContentType("application/json");
		resp.getWriter().write(respuesta_json.toString());
    }
    
}
