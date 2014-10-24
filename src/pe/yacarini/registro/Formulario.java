package pe.yacarini.registro;

import java.io.File;
import java.io.Serializable;

import pe.yacarini.registro.dao.AlumnoDAO;
import pe.yacarini.registro.modelo.Alumno;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import extras.Extras;

public class Formulario extends Activity{
	
	private FormularioAyuda formularioAyuda;
	//ruta de la foto tomada
	private String rutaArchivo;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
		
  		// Recibimos el alumno pasador por los extras del Intent
		Intent intent = getIntent();
  		final Alumno alumnoRecibido = (Alumno)intent.getSerializableExtra(Extras.ALUMNO_SELECCIONADO);  		
  		// --> mas abajo, si es diferente de null
  		//Toast.makeText(this, "Alumno: "+alumnoRecibido, Toast.LENGTH_LONG).show();
		
		formularioAyuda = new FormularioAyuda(this);		
		Button boton = (Button) findViewById(R.id.grabar);
		
		if(alumnoRecibido!=null){	// Si se recibe algun alumno por parametro
			Toast.makeText(this, "Alumno: "+alumnoRecibido, Toast.LENGTH_LONG).show();
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
		
		//Abrir la camara al clickear la foto
		ImageView foto = formularioAyuda.getFoto();
		//Atribuimos evento a la foto
		foto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Intent implicito para la camara
				Intent irParaCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				//Tomamos la ruta y nombre del archivo
				rutaArchivo = Environment.getExternalStorageDirectory().toString()
						+ "/" + System.currentTimeMillis()+".png";
				//creamos el archivo en la ruta especifica
				File archivo = new File(rutaArchivo);
				Uri localImagen = Uri.fromFile(archivo);
				
				//seteamos el intent pasando la foto
				irParaCamara.putExtra(MediaStore.EXTRA_OUTPUT, localImagen);
				// iniciamos el activity con el permiso '123' en caso se acepte la foto
				startActivityForResult(irParaCamara, 123);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode == 123){
			if(resultCode == Activity.RESULT_OK){
				formularioAyuda.cargarImagen(rutaArchivo);
			}else{
				rutaArchivo = null;
			}
		}
	}

}
