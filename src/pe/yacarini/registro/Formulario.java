package pe.yacarini.registro;

import java.io.Serializable;

import pe.yacarini.registro.dao.AlumnoDAO;
import pe.yacarini.registro.modelo.Alumno;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import extras.Extras;

public class Formulario extends Activity{
	
	private FormularioAyuda formularioAyuda;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
		
  		// Recibimos el alumno pasador por los extras del Intent
		Intent intent = getIntent();
  		final Alumno alumnoRecibido = (Alumno)intent.getSerializableExtra(Extras.ALUMNO_SELECCIONADO);  		
  		Toast.makeText(this, "Alumno: "+alumnoRecibido, Toast.LENGTH_LONG).show();
		
		formularioAyuda = new FormularioAyuda(this);		
		Button boton = (Button) findViewById(R.id.grabar);
		
		if(alumnoRecibido!=null){	// Si se recibe algun alumno por parametro
			boton.setText("Actualizar");
			formularioAyuda.colocarAlumnoEnFormulario(alumnoRecibido);	// llena los datos en formulario
		}
		
		
		boton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				// Alt + Arriba para subir el codigo de abajo				
				Alumno alumno = formularioAyuda.guardarFormularioAlumno();				
				// DAO para seguir patron de programaci�n
				AlumnoDAO dao = new AlumnoDAO(Formulario.this); // referencia del formulario (this)
				
				if(alumnoRecibido==null){
					dao.guardar(alumno);
				}else{
					alumno.setId(alumnoRecibido.getId());	// actualiza el registro segun el id
					dao.modificar(alumno);
				}
				
				dao.close(); // Cerramos el recurso por buena practica
				
				finish();
			}
		});
		
		/*
		Alumno alumno = formularioAyuda.guardarFormularioAlumno();  		
		// DAO para seguir patron de programaci�n
		AlumnoDAO dao = new AlumnoDAO(this); // referencia del formulario (this)
		dao.guardar(alumno);
		*/
	}

}
