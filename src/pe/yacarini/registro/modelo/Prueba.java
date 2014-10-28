package pe.yacarini.registro.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Prueba implements Serializable {
	private String fecha;
	private String materia;
	private String descripcion;
	private List<String> topicos = new ArrayList<String>();	
	
	public Prueba (String fecha, String materia) {
		super();
		this.fecha = fecha;
		this.materia = materia;
	}
	
	public Prueba(){
		
	}
	
	@Override
	public String toString() {
		return materia+" - "+fecha;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<String> getTopicos() {
		return topicos;
	}

	public void setTopicos(List<String> topicos) {
		this.topicos = topicos;
	}
	
}	
