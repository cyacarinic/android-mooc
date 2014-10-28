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
		//trabajo pesado en background
		
		String urlServidor = "http://api.joedayz.pe/android-mooc/api/v1.0/notas";
							//"http://192.168.1.33:5000/todo/api/v1.0/notas";
		
		AlumnoDAO dao = new AlumnoDAO(contexto);
		List<Alumno> alumnos = dao.getLista();
		dao.close();
		
		String datosJSON = new AlumnoConverter().toJSON(alumnos);
		
		WebClient client = new WebClient(urlServidor);
		String respuestaJSON = client.post(datosJSON);

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
