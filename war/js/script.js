
window.addEventListener("load", init);
var id_cuestionario = 1;

/*
	Inserta el nodo nuevoHijo como último hijo del nodo padre.
*/
function insertAsLastChild(padre, nuevoHijo) {

	padre.appendChild(nuevoHijo);
}

/*
	Inserta el nodo nuevoHijo como primer hijo del nodo padre.
*/
function insertAsFirstChild(padre, nuevoHijo) {

	padre.insertBefore(nuevoHijo, padre.firstChild);
}

/*
	InsertBeforeChild(padre,hijo,nuevoHijo
*/
function insertBeforeChild(padre, hijo, nuevoHijo) {

	padre.insertBefore(nuevoHijo, hijo);
}

/*
	Elimina del DOM el nodo pasado como parámetro.
*/
function removeElement(nodo) {

	nodo.parentNode.removeChild(nodo);
}

/*
	Devuelve el ancestro más cercano a node que case con el selector indicado
	como segundo parámetro o null si no existe ninguno; node ha de ser un nodo 
	inferior en el árbol a document.body.
*/
function queryAncestorSelector (node, selector) {

      var parent= node.parentNode;
      var all = document.querySelectorAll(selector);
      var found= false;

      while (parent !== document && !found) {

        for (var i = 0; i < all.length && !found; i++) {

          found= (all[i] === parent)?true:false;
        }

        parent= (!found)?parent.parentNode:parent;
      }

      return (found)?parent:null;
    }


function addCruz(bloque) {

	var cruz = document.createElement("div");
	cruz.className = "borra";
	cruz.innerHTML = "&#9746;"

	cruz.addEventListener("mouseover", cursor_pointer, false);
	cruz.addEventListener("mouseout", cursor_default, false);
	cruz.addEventListener("click", borra_pregunta, false);

	insertAsFirstChild(bloque, cruz);
}



function addFormPregunta(seccion) {

	var html = "<ul>" +
        	   "	<li>" +
          	   "		<label>Enunciado de la pregunta:</label>" +
          	   "		<input type='text' name='" + seccion.id + "_pregunta'>" + 
        	   "	</li>" +
        	   "	<li>" +
          	   "		<label>Respuesta:</label>" +
          	   "		<input type='radio' name='" + seccion.id + "_respuesta' value='verdadero' checked>Verdadero" +
          	   "		<input type='radio' name='" + seccion.id + "_respuesta' value='falso'>Falso" +
        	   "	</li>" +
        	   "	<li>" +
          	   "		<input type='button' value='Añadir nueva pregunta'>" +
        	   "	</li>" +
      		   "</ul>";

    var form_pregunta = document.createElement("div");
    form_pregunta.className = "formulario";
    form_pregunta.innerHTML = html;

    boton = form_pregunta.firstChild.children[2].firstChild.nextSibling;
    boton.addEventListener("click", addPregunta);

    insertBeforeChild(seccion, seccion.firstChild.nextSibling.nextSibling, form_pregunta);

    return form_pregunta;
}


function cursor_pointer() { document.body.style.cursor = "pointer"; }
function cursor_default() { document.body.style.cursor = "default"; }


function borra_pregunta(e) { 

	var bloque = queryAncestorSelector(e.currentTarget, ".bloque");
	var cuestionario = bloque.parentNode;
	
	var seccion = queryAncestorSelector(e.currentTarget, "section");
	var tema_cuestionario = seccion.firstChild.nextSibling.tema;
	var pregunta = bloque.firstChild.nextSibling.firstChild.data;
	
	var borraPregunta = function(resp) {
		
		// Funcion llamada si la peticion borrar pregunta se realiza correctamente
		if (resp.result != null) {
			
			removeElement(bloque);
			
			// Si el cuestionario se queda sin preguntas..
			if (cuestionario.getElementsByClassName("bloque").length == 0) {
				
				// Funcion llamada si la peticion borrar cuestionario se realiza correctamente
				var borraCuestionario = function(resp) {
					
					if (resp.result != null) {
						
						var indice = document.getElementsByTagName("nav")[0].firstChild.nextSibling;
						var found = false;
						
						for (var i = 0; i < indice.childElementCount && !found; i++) {
							
				        	if (indice.children[i].firstChild.innerHTML 
				        		== cuestionario.firstChild.nextSibling.tema) {

				        		removeElement(indice.children[i]);
				        		found = true;
				        	}
				        }
						
						removeElement(cuestionario);

					} else if (resp.error != null) {
						
						alert(resp.error.message);
					}
					
				}
				
				// Segunda consulta lanzada: borrar cuestionario
				$(document).ready(function() {
					
					$.ajax({
			        	type: "GET",
			       		url: "http://localhost:8888/borracuestionario?tema=" + cuestionario.firstChild.nextSibling.tema,
			        	contentType: "application/json; charset=utf-8",
			        	dataType: "json",
			      		success: borraCuestionario,
			      		error: function() { alert("ERROR");}
						
			    	});
				});
				
			}

		} else if (resp.error != null) {
			
			alert(resp.error.message);
		}
	}
	
	//Primera consulta lanzada: borrar pregunta
	$(document).ready(function() {
		
		$.ajax({
        	type: "GET",
       		url: "http://localhost:8888/borrapregunta?pregunta=" + pregunta + 
       												"&tema=" + tema_cuestionario,
        	contentType: "application/json; charset=utf-8",
        	dataType: "json",
      		success: borraPregunta,
      		error: function() { alert("ERROR");}
			
    	});
	});
}

function borrarDatos() {
	
	var confirmacion = confirm("¿Estas seguro de que deseas borrar todos los datos?");
	
	if (confirmacion) {
		
		var borraDatos = function(resp) {
			
			document.location.href = document.location.href;
		}
		
		$(document).ready(function() {
			
			$.ajax({
	        	type: "GET",
	       		url: "http://localhost:8888/borradatos",
	        	contentType: "application/json; charset=utf-8",
	        	dataType: "json",
	      		success: borraDatos,
	      		error: function() { alert("ERROR");}
				
	    	});
		});
	}
}

function init() {

    var boton_form = document.getElementsByName("crea")[0];
    boton_form.addEventListener("click", addCuestionario, false);
    
    var borra_datos = document.getElementById("borrar");
    borra_datos.addEventListener("click", borrarDatos, false);
    
    var listaCuestionarios = function(resp) {
    	
    	// Recorrer cuestionarios existentes
    	for (var n_cuest in resp.result) {
    		
    		dibujaNuevoCuestionario(resp.result[n_cuest]);
    		
    		var listaPreguntas = function(resp2) {
    			
    			if (resp.result != null) {
    				
    				// Recorrer preguntas existentes (de cada cuestionario)
    				for (var n_preg in resp2.result) {
    					
    					var respuesta;
    					if (resp2.result[n_preg].respuesta == "verdadero") {
    						
    						respuesta = true;
    					} else respuesta = false;
    					
    					var secciones =  document.getElementsByTagName("section");
    					for (var n in secciones) {

    						if (secciones[n].firstChild.nextSibling.tema == resp2.result[n_preg].tema) {
    							
    	    					dibujaNuevaPregunta(secciones[n], resp2.result[n_preg].pregunta, respuesta);
    						}
    					}		    		
    				}
	
    			} else if (resp2.error != null) {
    				
    				alert(resp2.error.message);
    			}
    		}
    		
    		// Segunda consulta invocada: listar preguntas
        	//$(document).ready(function() {
        		
        		$.ajax({
                	type: "GET",
               		url: "http://localhost:8888/listapreguntas?tema=" + resp.result[n_cuest],
                	contentType: "application/json; charset=utf-8",
                	dataType: "json",
              		success: listaPreguntas,
              		error: function() { alert("ERROR");}
        			
            	});
        	//});
    	}
    	
    	
    }
    
    // primera consulta invocada: listar cuestionarios
    $(document).ready(function() {
		
		$.ajax({
        	type: "GET",
       		url: "http://localhost:8888/listacuestionarios",
        	contentType: "application/json; charset=utf-8",
        	dataType: "json",
      		success: listaCuestionarios,
      		error: function() { alert("ERROR");}
			
    	});
	});

}

function dibujaNuevoCuestionario(tema) {
	
	var seccion = document.createElement("section");
	seccion.id = "c" + id_cuestionario;
	id_cuestionario++;
	
	var encabezado = document.createElement("encabezado-cuestionario");
	encabezado.tema = tema;

	/*
	var h2 = document.createElement("h2");

	var img = document.createElement("img");
	img.alt = tema_form.value;
	insertAsFirstChild(h2, document.createTextNode(" "));
	insertAsLastChild(h2, img);
	insertAsLastChild(h2, document.createTextNode("Cuestionario sobre " + tema));*/
	

	insertAsFirstChild(seccion, document.createTextNode(" "));
	//insertAsLastChild(seccion, h2);
	insertAsLastChild(seccion, encabezado);
	insertAsLastChild(seccion, document.createTextNode(" "));
	insertAsLastChild(document.getElementsByTagName("main")[0], seccion);

	var li = document.createElement("li");
	var a = document.createElement("a");
	a.href = "#" + seccion.id;
	a.textContent = tema;

	insertAsFirstChild(li, a);
	insertAsLastChild(document.getElementsByTagName("nav")[0].firstChild.nextSibling, li);

	var form_pregunta = addFormPregunta(seccion);
	//addWikipedia(tema_form.value, form_pregunta);
	//addFlickr(tema_form.value, img);
	
	return seccion;
	
}

function dibujaNuevaPregunta(seccion, cont_pregunta, cont_respuesta) {
	
	var pregunta_form = document.getElementsByName(seccion.id + "_pregunta")[0];
	var respuestas_form = document.getElementsByName(seccion.id + "_respuesta");
	
	var bloque = document.createElement("div");
	bloque.className = "bloque";

	var pregunta = document.createElement("div");
	pregunta.className = "pregunta";
	pregunta.textContent = cont_pregunta;

	var respuesta = document.createElement("div");
	respuesta.className = "respuesta";

	if (cont_respuesta == true) {
		respuesta.setAttribute("data-valor", "true");
	} else {
		respuesta.setAttribute("data-valor", "false");
	}
	
	insertAsFirstChild(bloque, pregunta);
	insertAsLastChild(bloque, respuesta);
	addCruz(bloque);
	insertAsLastChild(seccion, bloque);

	pregunta_form.value = "";
	respuestas_form[0].checked = true;
	respuestas_form[1].checked = false;
}

function addPregunta(e) {

	var seccion = queryAncestorSelector(e.currentTarget, "section");
	var pregunta_form = document.getElementsByName(seccion.id + "_pregunta")[0];
	var respuestas_form = document.getElementsByName(seccion.id + "_respuesta");
	var tema_cuestionario = seccion.firstChild.nextSibling.tema;
	
	
	if (pregunta_form.value == "" || (!respuestas_form[0].checked && !respuestas_form[1].checked)) {

		window.alert("ERROR: Hay campos sin rellenar en el formulario.");

	} else {
		
		if (!respuestas_form[0].checked) {
			
			var respuesta_form = "verdadero";
			
		} else {
			
			var respuesta_form = "falso";
		}
		
		var addNuevaPregunta = function(resp) {
			
			if (resp.result != null) {
				
				var resp = respuestas_form[0].checked;
				
				dibujaNuevaPregunta(seccion, pregunta_form.value, resp);
				
			} else if (resp.error != null) {
				
				alert(resp.error.message);
			}
			
		};
		
		$(document).ready(function() {
			$.ajax({
	        	type: "GET",
	       		url: "http://localhost:8888/nuevapregunta?pregunta=" + pregunta_form.value +
	       												"&respuesta=" + respuesta_form +
	       												"&tema=" + tema_cuestionario,
	        	contentType: "application/json; charset=utf-8",
	        	dataType: "json",
	      		success: addNuevaPregunta,
	      		error: function() { alert("ERROR");}
				
	    	});
		});
		
	}
}

function addCuestionario(e) {
	
	
	var formulario = queryAncestorSelector(e.currentTarget, "div");
	var tema_form = formulario.firstChild.nextSibling.firstChild.nextSibling.firstChild.nextSibling.nextSibling.nextSibling;
	
	if (tema_form.value == "") {

		window.alert("ERROR: Hay campos sin rellenar en el formulario.");

	} else {
		
		var addNuevoCuestionario = function(resp) {
			
			if (resp.result != null) {
				
				dibujaNuevoCuestionario(tema_form.value);
				
			} else if (resp.error != null) {
				
				alert(resp.error.message);
			}
			
			tema_form.value = "";
		};
		
		$(document).ready(function() {
			
			$.ajax({
	        	type: "GET",
	       		url: "http://localhost:8888/nuevocuestionario?tema=" + tema_form.value,
	        	contentType: "application/json; charset=utf-8",
	        	dataType: "json",
	      		success: addNuevoCuestionario,
	      		error: function() { alert("ERROR");}
				
	    	});
		});
	}
}

/*
function addWikipedia(termino, nodo_formulario) {

	// Si no hay error en la peticion AJAX
	var addTexto = function(resp) {

		var contenido_wiki = "";

		for (var pag in resp.query.pages) {
			if (pag != "-1") {
  				contenido_wiki += resp.query.pages[pag].extract;
  			}
		}
		
 	 	$(nodo_formulario).children().first().before("<div class='wiki'>" + contenido_wiki + "</div>");
	};

	$(document).ready(function() {
	
		$.ajax({
        	type: "GET",
       		url: "https://es.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&exintro&explaintext&continue&titles=" + termino + "&callback=?",
        	contentType: "application/json; charset=utf-8",
        	dataType: "json",
      		success: addTexto
    	});
	});
}

function addFlickr(termino, nodo_imagen) {

	var API_KEY = "766edf7e987092a2e56f72f84408ff91";

	var addImagen = function(resp) {

		nodo_imagen.src = resp.sizes.size[0].source;
	}

	$(document).ready(function() {
	
		$.ajax({
        	type: "GET",
       		url: "https://api.flickr.com/services/rest/?&method=flickr.photos.search&api_key=" + API_KEY + "&text=" + termino + "&format=json&per_page=10&media=photos&sort=relevance&jsoncallback=?",
        	contentType: "application/json; charset=utf-8",
        	dataType: "json",
      		success: function(resp) {

      			if (resp.photos.photo.length > 0) {
      				var id_foto = resp.photos.photo[0].id;

					$.ajax({
	        			type: "GET",
	       				url: "https://api.flickr.com/services/rest/?method=flickr.photos.getSizes&api_key=" + API_KEY + "&photo_id=" + id_foto + "&format=json&jsoncallback=?",
	        			contentType: "application/json; charset=utf-8",
	        			dataType: "json",
	      				success: addImagen
	    			});
	    			
	    		} else {

	    			nodo_imagen.src = "img/globe_east_540.jpg";
	    		}
      		}
    	});
	});
}*/


