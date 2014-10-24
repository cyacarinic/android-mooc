package pe.yacarini.registro.task;

import java.util.List;

import pe.yacarini.registro.AlumnoConverter;
import pe.yacarini.registro.WebClient;
import pe.yacarini.registro.dao.AlumnoDAO;
import pe.yacarini.registro.modelo.Alumno;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class EnviaAlumnosTask extends AsyncTask<Integer, Double, String>{

	private final Activity contexto; //final, nadie lo debe modificar
	private ProgressDialog dialogoDeProgreso;

	public EnviaAlumnosTask(Activity context){
		this.contexto = context;
	}
	
	@Override
	protected String doInBackground(Integer... params) {
		// acá se realiza el trabajo pesado
		
		String respuestaJSON;
		
		try{
			String urlServidor = 
					"http://andrpod-mobile.joedayz.cludbees.net/alumnos";								
			//cargamos los alumnos en "dao"
			AlumnoDAO dao = new AlumnoDAO(contexto); //context != this -> ListaAlumnos.this
			List<Alumno> alumnos = dao.getLista();
			dao.close();
			
			//implementaremos la clase AlumnoConverter con el metodo toJSON
			//que reciba por parámetro la lista de alumnos
			String datosJSON = new AlumnoConverter().toJSON(alumnos);
			
			// Recibiremos la respuesta del cliente de la clase WebClient
			WebClient cliente = new WebClient(urlServidor);
			respuestaJSON = cliente.post(datosJSON); //post de los datos JSON
		}catch(Exception e){
			respuestaJSON = e.getMessage();
			Log.i("--> Error : ", respuestaJSON);
		}
		
		return respuestaJSON;
	}
	
	@Override
	protected void onPreExecute() {
		dialogoDeProgreso = ProgressDialog.show(contexto, "Espera pe...", 
				"Sincronizando tus datos con el server.",true,true);
	}
	
	@Override
	protected void onPostExecute(String result) {
		// se lanza despues de la tarea pesada (do in bakcground)
		dialogoDeProgreso.dismiss(); // cerramos la carga
		Toast.makeText(contexto, 
				result, Toast.LENGTH_LONG)
				.show();
	}

}
