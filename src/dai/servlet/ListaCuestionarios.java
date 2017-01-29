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
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class ListaCuestionarios extends HttpServlet {
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws IOException, ServletException {
   
    	JSONObject respuesta_json = new JSONObject();
    	
    	String userName = req.getUserPrincipal().getName();
    	Key userKey = KeyFactory.createKey("Usuario", userName);
    	
    	Query consulta = new Query("Cuestionario").setAncestor(userKey).addSort("tema", SortDirection.ASCENDING);

        List<Entity> cuestionarios = datastore.prepare(consulta).asList(FetchOptions.Builder.withDefaults());
        //List<String> respuesta = new ArrayList<String>();
        JSONArray array = new JSONArray();
    		
        // Rellenar un array json para usar en la respuesta
        for (Entity cuestionario : cuestionarios) {
        	
        	//respuesta.add((String)cuestionario.getProperty("tema"));
        	array.put(cuestionario.getProperty("tema")); 	
        }
        
        // Preparar la respuesta con el listado de temas
		try {
			respuesta_json.put("result", array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Servlet ListaCuestionarios: Recibido <-- ");
		System.out.println("Servlet ListaCuestionarios: Respuesta --> " + respuesta_json.toString());
		
		// Preparar y escribir respuesta
		resp.setContentType("application/json");
		resp.getWriter().write(respuesta_json.toString());
    }
    
}
