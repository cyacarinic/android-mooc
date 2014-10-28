package pe.yacarini.registro;

import java.util.List;

import org.json.JSONException;
import org.json.JSONStringer;

import pe.yacarini.registro.modelo.Alumno;

public class AlumnoConverter {

	public String toJSON(List<Alumno> alumnos) {
		/* Estructura del JSON:
		 {"alumnos":[
			{"nombre":"Claudio","nota":17.0},
			{"nombre":"Mili","nota":20.0}
		 ]}
		 js.key("id").value(alumno.getId());
				js.key("direccion").value(alumno.getDireccion());
				js.key("site").value(alumno.getSite());
				js.key("telefono").value(alumno.getTelefono());
				
		*/
		try{
			//try catch para controlar excepciones (seccion checkeada)
			JSONStringer js = new JSONStringer();
			
			js.object().key("alumnos").array();
			for(Alumno alumno : alumnos){
				js.object();
				js.key("nombre");
				js.value(alumno.getNombre());
				js.key("nota").value(alumno.getNota());
				js.endObject();
			}
			js.endArray();
			
			return js.toString(); // String JSON
		}catch(JSONException e){
			throw new RuntimeException(e);
		}
	}

}
