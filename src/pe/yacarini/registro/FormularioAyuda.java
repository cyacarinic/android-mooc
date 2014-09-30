package pe.yacarini.registro;

import pe.yacarini.registro.modelo.Alumno;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

public class FormularioAyuda {
	
	private EditText editNombre;
	private EditText editSite;
	private EditText editTelefono;
	private EditText editDireccion;
	private RatingBar ratingNota;
	// instanciamos referencias para la foto y el alumno en cuestion
	private ImageView foto;
	private Alumno alumno;

	public FormularioAyuda(Formulario formu){
		// Ctrl + 1 -> Convertir variable local a campo
		editNombre = (EditText) formu.findViewById(R.id.nombre); 
  		editSite = (EditText) formu.findViewById(R.id.site);
  		editTelefono = (EditText) formu.findViewById(R.id.telefono);
  		editDireccion = (EditText) formu.findViewById(R.id.direccion);
  		ratingNota = (RatingBar) formu.findViewById(R.id.nota);
  		//iniciamos las instancias del alumno y su foto
  		foto = (ImageView)formu.findViewById(R.id.foto);
  		alumno = new Alumno();
	}

	public Alumno guardarFormularioAlumno() {
		// TODO Auto-generated method stub
		Alumno alumno = new Alumno();
  		alumno.setNombre(editNombre.getText().toString());
  		alumno.setSite(editSite.getText().toString());
  		alumno.setTelefono(editTelefono.getText().toString());
  		alumno.setDireccion(editDireccion.getText().toString());
  		alumno.setNota(Double.valueOf(ratingNota.getRating()));
  		
  		return alumno;
	}

	public void colocarAlumnoEnFormulario(Alumno alumnoRecibido) {
		// Carga los datos del alumno recibido por parametro
		editNombre.setText(alumnoRecibido.getNombre());
		editSite.setText(alumnoRecibido.getSite());
		editTelefono.setText(alumnoRecibido.getTelefono());
		editDireccion.setText(alumnoRecibido.getDireccion());
		ratingNota.setRating(alumnoRecibido.getNota().floatValue());
		
	}
	
	public ImageView getFoto(){
		return foto;
	}

	public void cargarImagen(String rutaArchivo) {
		// TODO Auto-generated method stub
		// establecemos la ruta al objeto alumno
		alumno.setFoto(rutaArchivo);
		//creamos la imagen con el tamaño real
		Bitmap imagen = BitmapFactory.decodeFile(rutaArchivo);
		// escalamos la imagen para mostrarla en espacio de 100x100
		Bitmap imagenReducida = Bitmap.createScaledBitmap(imagen, 100, 100, true);
		
		foto.setImageBitmap(imagenReducida);
	}


}
