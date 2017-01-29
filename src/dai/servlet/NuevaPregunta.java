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
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class NuevaPregunta extends HttpServlet {
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws IOException, ServletException {
    	
    	// Obtener los parametros pregunta, respuesta y tema de la peticion
    	String tema = req.getParameter("tema");
    	String pregunta = req.getParameter("pregunta");
    	String respuesta = req.getParameter("respuesta");
    	
    	JSONObject respuesta_json = new JSONObject();
    	
    	try {
    	
    		// Si algun parametro no se ha configurado correctamente..
	    	if (pregunta == null || pregunta == "") {
	    		
	    		JSONObject message = new JSONObject();
	    		message.put("message", "se esperaba el parámetro pregunta");
	    		respuesta_json.put("error", message);
	    		
	    	} else if (respuesta == null || respuesta == "") {
	    		
	    		JSONObject message = new JSONObject();
	    		message.put("message", "se esperaba el parámetro respuesta");
	    		respuesta_json.put("error", message);
	    	
    		} else if (tema == null || tema == "") {
	    		
	    		JSONObject message = new JSONObject();
	    		message.put("message", "se esperaba el parámetro tema");
	    		respuesta_json.put("error", message);
	    		
	    	// Si todos los parametros se han especificado correctamente..
	    	} else {
	    		
	    		// Obtener el identificador del usuario
	    		String userName = req.getUserPrincipal().getName();
	    	    Key userKey = KeyFactory.createKey("Usuario", userName);
	    	    
	    	    Filter temaFilterCuestionario = new FilterPredicate("tema", FilterOperator.EQUAL, tema); 
	    	    Query consultaCuestionario = new Query("Cuestionario").setAncestor(userKey).setFilter(temaFilterCuestionario);
	            List<Entity> cuestionarios = datastore.prepare(consultaCuestionario).asList(FetchOptions.Builder.withDefaults());
	            
	            // Si existe un cuestionario con el tema pasado por argumentos..
	            if (cuestionarios.size() > 0) {
	    	
		    	    Filter preguntaFilter = new FilterPredicate("pregunta", FilterOperator.EQUAL, pregunta); // Filtro para obtener las preguntas con el enunciado pasado por parametros
		    	    Filter temaFilter = new FilterPredicate("tema", FilterOperator.EQUAL, tema); // Filtro para obtener las preguntas con el tema pasado por parametros
		    	    CompositeFilter pregunta_tema_filter = CompositeFilterOperator.and(preguntaFilter, temaFilter); // Combinacion de los anteriores
		            
		    	    Query consulta = new Query("Pregunta").setAncestor(userKey).setFilter(pregunta_tema_filter);
		            List<Entity> preguntas = datastore.prepare(consulta).asList(FetchOptions.Builder.withDefaults());
		    		
		            // Si el usuario no tenia ya una pregunta con el enunciado y temas especificados..
		            if (preguntas.size() == 0) {
		            	
		            	// Crear una entidad con la entidad usuario como ancestro
			    		Entity nueva_pregunta = new Entity("Pregunta", userKey);
			    		nueva_pregunta.setIndexedProperty("pregunta", pregunta);
			    		nueva_pregunta.setIndexedProperty("respuesta", respuesta);
			    		nueva_pregunta.setIndexedProperty("tema", tema);
			    		
			    		// Almacenar en datastore
			    		datastore.put(nueva_pregunta);
			    		
			    		// Responder a la peticion ---> Pregunta creada CORRECTAMENTE
			    		JSONArray array = new JSONArray();
						respuesta_json.put("result", array);	
						
					// Si el usuario ya tenia una pregunta con el enunciado y temas especificados..
		            } else {
		            	
		            	JSONObject message = new JSONObject();
		            	String message_error = "la pregunta '" + pregunta + "' con tema " + tema + " ya existe en la base de datos";
		            	message.put("message", message_error);
		            	respuesta_json.put("error", message);    	
		            }
		            
		        // Si no existe ningun cuestionario con el tema especificado en la base de datos
	            } else {
	            	
	            	JSONObject message = new JSONObject();
	            	String message_error = "no existe ningun cuestionario con tema " + tema + "  en la base de datos";
	            	message.put("message", message_error);
	            	respuesta_json.put("error", message);  
	            	
	            }
	    		
	    	} 
    	
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		
    	System.out.println("Servlet NuevaPregunta: Recibido <-- pregunta=" + pregunta + ", respuesta= " + respuesta + ", tema=" + tema);
		System.out.println("Servlet NuevaPregunta: Respuesta --> " + respuesta_json.toString());
		
		// Preparar y escribir respuesta
		resp.setContentType("application/json");
		resp.getWriter().write(respuesta_json.toString());
    }
    
}
    	
