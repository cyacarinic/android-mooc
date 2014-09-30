package pe.yacarini.registro;

import java.util.List;

import pe.yacarini.registro.dao.AlumnoDAO;
import pe.yacarini.registro.modelo.Alumno;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import extras.Extras;

/*
 * 
 * Se adicionan los metodos llamar y visitar Site
 * Uso de Intents Implicitos
 * 
 * 
 * */

public class ListaAlumnos extends Activity {

    private ListView lista; // La convertimos en campo local
	private Alumno alumnoSeleccionado;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_de_alumnos);
        
        lista = (ListView) findViewById(R.id.lista);
        
        registerForContextMenu(lista); // Se relaciona el menu contextual con el LisvView Lista
          
        lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				
				alumnoSeleccionado = (Alumno)adapter.getItemAtPosition(position);
				// Creamos el salto de ListaAlumnos a Formulario
				Intent irParaFormulario = new Intent(ListaAlumnos.this, Formulario.class);
				irParaFormulario.putExtra(Extras.ALUMNO_SELECCIONADO, alumnoSeleccionado);
				
				startActivity(irParaFormulario); //Iniciamos la actividad
			}
			
		});
        
        lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int position, long id) {
				
				Toast.makeText(ListaAlumnos.this,
						"Opciones de "+adapter.getItemAtPosition(position),
						Toast.LENGTH_SHORT).show();
				alumnoSeleccionado = (Alumno)adapter.getItemAtPosition(position);
				
				// con FALSE: el click largo escucha tmbn al menu contextual o al click simple
				// con TRUE: solo escucha al OnItemLongClickListener
				return false;
			}
		});
        
    }
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		
		MenuItem menuLlamar = menu.add("Llamar");
		menuLlamar.setOnMenuItemClickListener(new OnMenuItemClickListener() {			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// Utilizamos el intent Implicito 'Intent.ACTION_CALL'
				Intent irParaTelefono = new Intent (Intent.ACTION_CALL);
				Uri llamar_a = Uri.parse("tel:"+alumnoSeleccionado.getTelefono());
				irParaTelefono.setData(llamar_a);

				startActivity(irParaTelefono);

				return false;
			}

		});
		
		menu.add("Enviar un SMS");

		MenuItem menuSite = menu.add("Visitar Sitio Web");
		menuSite.setOnMenuItemClickListener(new OnMenuItemClickListener() {			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// Utilizamos el intent Implicito 'Intent.ACTION_VIEW'
				Intent irParaSite = new Intent (Intent.ACTION_VIEW);
				Uri localSite = Uri.parse("http://"+alumnoSeleccionado.getSite());
				irParaSite.setData(localSite);

				startActivity(irParaSite);

				return false;
			}

		});

		MenuItem menuEliminar = menu.add("Eliminar"); // se crea una variable para poder manejar eventos
		menuEliminar.setOnMenuItemClickListener(new OnMenuItemClickListener() {			
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				AlumnoDAO dao = new AlumnoDAO(ListaAlumnos.this);
				dao.eliminar(alumnoSeleccionado);
				
				cargarLista();
				
				dao.close();
				return false;
			}

		});
		menu.add("Ver en el mapa");
		menu.add("Enviar un mail");
		
		
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	private void cargarLista() {
		// TODO Auto-generated method stub
		AlumnoDAO dao = new AlumnoDAO(this);
		List<Alumno> alumnos = dao.getLista();
		dao.close();
		int layout = android.R.layout.simple_list_item_1;
		ArrayAdapter<Alumno> adaptador = 
				new ArrayAdapter<Alumno>(this, layout, alumnos);
		
		
		lista.setAdapter(adaptador);
		
	}
	
    
    @Override
    protected void onResume() {
    	super.onResume();
    	cargarLista();
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.lista_alumnos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
		case R.id.nuevo:
			Intent irFormulario = new Intent(this, Formulario.class);
			startActivity(irFormulario);
			break;

		default:
			break;
		}
        return super.onOptionsItemSelected(item);
    }
    
}
