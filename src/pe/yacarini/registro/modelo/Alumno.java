package pe.yacarini.registro.modelo;

import java.io.Serializable;

public class Alumno implements Serializable { // Serializamos para pasar como extra de un Intent
	// Datos del formulario
	private Long id;
	private String nombre;
	private String site;
	private String direccion;
	private String telefono;
	private Double nota;
	private String foto;
	
	// Ctrl + 3 --> buscar Generate Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public Double getNota() {
		return nota;
	}
	public void setNota(Double nota) {
		this.nota = nota;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	// Click izquierdo -> Source -> Generate toString -> Seleccionar campos
	@Override
	public String toString() {
		return getNombre(); // sino bota mucha vaina
	}
	

}
