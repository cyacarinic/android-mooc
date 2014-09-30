package pe.yacarini.registro.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import pe.yacarini.registro.modelo.Alumno;

public class AlumnoDAO extends SQLiteOpenHelper{// Se extiende SQLiteOpenHelper 

	private static final String DATABASE = "RegistroYacarini"; // nombre de la db
	private static final int VERSION = 1; // final xq son fijos y se evita el cambio

	public AlumnoDAO(Context context) {
		super(context, DATABASE, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// permite crear la tabla ALUMNO 
		// con las columnas de la clase alumno
		String query = "CREATE TABLE Alumnos (id INTEGER PRIMARY KEY," +	// tipo integer
				"nombre TEXT UNIQUE NOT NULL, " +
				"telefono TEXT, " +
				"direccion TEXT, " +
				"site TEXT, " +
				"foto TEXT, " +
				"nota REAL);";
		db.execSQL(query);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// verifica si la tabla ya existe
		String query = "DROP TABLE IF EXIST Alumnos";
		//La borramos
		db.execSQL(query);
		// volvemos a crear la DB
		this.onCreate(db);
		
	}
	
	public void guardar(Alumno alumno) {
		// Se guardar� en SQLite
		ContentValues values = new ContentValues();
		
		values.put("nombre",alumno.getNombre());
		values.put("site",alumno.getSite());
		values.put("direccion",alumno.getDireccion());
		values.put("telefono",alumno.getTelefono());
		values.put("nota",alumno.getNota());
		values.put("foto", alumno.getFoto());
		
		getWritableDatabase().insert("Alumnos", null, values);
	}

	// Retornara la lista de alumnos del query
	public List<Alumno> getLista() {
		String[] columnas = {"id", "nombre", "site", "telefono", "direccion",
				"foto", "nota"}; // columnas de la consulta
		// indice empieza desde 0
		Cursor cursor = getWritableDatabase().query("Alumnos",
				columnas , null, null, null,	null, null); // retorna un cursor
		
		
		ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
		
		while (cursor.moveToNext()){
			// mientras se pueda iterar en los registros
			Alumno alumno = new Alumno();
			alumno.setId((cursor.getLong(0)));
			alumno.setNombre(cursor.getString(1));
			alumno.setSite(cursor.getString(2));
			alumno.setTelefono(cursor.getString(3));
			alumno.setDireccion(cursor.getString(4));
			alumno.setFoto(cursor.getString(5));
			alumno.setNota(cursor.getDouble(6));
			
			alumnos.add(alumno);
		}
		return alumnos;
	}

	public void eliminar(Alumno alumnoSeleccionado) {
		String [] args = {alumnoSeleccionado.getId().toString()};
		getWritableDatabase().delete("Alumnos", "id=?", args);		
	}

	public void modificar(Alumno alumno) {
		ContentValues values = new ContentValues();		
		values.put("nombre",alumno.getNombre());
		values.put("site",alumno.getSite());
		values.put("direccion",alumno.getDireccion());
		values.put("telefono",alumno.getTelefono());
		values.put("nota",alumno.getNota());
		values.put("foto", alumno.getFoto());
		
		String [] id = {alumno.getId().toString()};		
		getWritableDatabase().update("Alumnos", values, "id=?", id);
	}

}
