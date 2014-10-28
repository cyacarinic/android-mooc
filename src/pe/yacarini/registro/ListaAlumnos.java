package pe.yacarini.registro;

import java.util.List;

import pe.yacarini.registro.dao.AlumnoDAO;
import pe.yacarini.registro.modelo.Alumno;
import pe.yacarini.registro.task.EnviaAlumnosTask;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
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
		
		MenuItem menuSMS = menu.add("Enviar un SMS");
		menuSMS.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent irParaSMS = new Intent(Intent.ACTION_VIEW);
				Uri dataSMS = Uri.parse("sms:"+alumnoSeleccionado.getTelefono());
				irParaSMS.setData(dataSMS);
				irParaSMS.putExtra("sms_body", "Estimado "+alumnoSeleccionado+", su nota " +
						"es: "+alumnoSeleccionado.getNota());
				startActivity(irParaSMS);
				/*
				 * TODO
				SmsManager smsManager	=	SmsManager.getDefault();	
				PendingIntent	sentIntent	=	PendingIntent.getActivity(ListaAlumnos.this, 0, null, 0);	
				if(PhoneNumberUtils.isWellFormedSmsAddress(alumnoSeleccionado.getTelefono()))	{	
				  smsManager.sendTextMessage(alumnoSeleccionado.getTelefono(), null,	"Su nota es"+alumnoSeleccionado.getNota(), sentIntent, null);	
				  Toast.makeText(ListaAlumnos.this,"SMS enviado con exito!!!",	Toast.LENGTH_LONG).show();
				}else {	
				  Toast.makeText(ListaAlumnos.this, "Fallo	el SMS -­‐ intente nuevamente!!!", Toast.LENGTH_LONG).show();	
				}*/
				
				return false;
			}
		});

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
		MenuItem verEnMapa = menu.add("Ver en el mapa");
		verEnMapa.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent irParaMapa = new Intent(Intent.ACTION_VIEW);
				Uri dataMapa = Uri.parse("geo:0,0?z=14&q="+alumnoSeleccionado.getDireccion());
				irParaMapa.setData(	dataMapa );
				
				try{
					startActivity(irParaMapa);
				}catch(Exception e){
					Toast.makeText(ListaAlumnos.this, "<{Warning: Verifique tener instalado un gestor de mapas}/>", Toast.LENGTH_LONG).show();
				}
				
				return false;
			}
		});
		MenuItem menuEmail = menu.add("Enviar un mail");
		menuEmail.setOnMenuItemClickListener(new OnMenuItemClickListener() {			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
				Intent irParaMail = new Intent(Intent.ACTION_SEND);
				irParaMail.setType("message/rfc882");
				irParaMail.putExtra(Intent.EXTRA_EMAIL,
						new String[]{"informes@joedayz.pe"});
				irParaMail.putExtra(Intent.EXTRA_SUBJECT, 
						"Comentarios sobre el curso de Android");
				irParaMail.putExtra(Intent.EXTRA_TEXT, 
						"Bale Berga la Bida y el Curso X)");
				
				try{
					startActivity(irParaMail);
				}catch(Exception e){
					Toast.makeText(ListaAlumnos.this, "<{Warning: Verifique tener instalado un gestor de e-mail}/>",
							Toast.LENGTH_LONG).show();
				}
				
				return false;
			}
		});
		
		
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	private void cargarLista() {
		// TODO Auto-generated method stub
		AlumnoDAO dao = new AlumnoDAO(this);
		List<Alumno> alumnos = dao.getLista();
		dao.close();
		/* Ya no usaremos el simple list sino un layou personalizado 
		int layout = android.R.layout.simple_list_item_1;*/
		/* int layout = R.layout.linea_lista;
		 * Añadiremos nuestro propio Layout e Inflater
		 * para que todo lo haga el adaptador
		ArrayAdapter<Alumno> adaptador = 
				new ArrayAdapter<Alumno>(this, layout, alumnos);*/
		ListaAlumnoAdapter adaptador = new
				ListaAlumnoAdapter(alumnos, this);
		//LayoutInflater inflater = getLayoutInflater();
		
		
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
				
			case R.id.recibir_pruebas:
				Intent irParaPruebas = new Intent(this, PruebasActivity.class);
				startActivity(irParaPruebas);
				break;
				
			case R.id.enviar_alumnos:
				
				EnviaAlumnosTask task = new EnviaAlumnosTask(this);
				task.execute();
				
				/* Genera error si llamamos la tarea de un servicio desde el mismo hilo
				 * de la interfaz de usuario (Network on main trheat exception), se sobre carga.
				 * --> Solucion monse: crear un emulador anticucho
				 * --> solucion normal, asigarlo a otro Thread
				 */
				/*
				 * Simplificamos implementando EnviarAlumnosTask
				 * 
				Thread tareaPesada = new Thread(){
					@Override
					public void run() {
						
						// ----> Nos llevamos la tarea pesada a EnviaAlumnoTask
						// Insertamos la tarea pesada
						String urlServidor = 
								"http://andrpod-mobile.joedayz.cludbees.net/alumnos";								
						//cargamos los alumnos en "dao"
						AlumnoDAO dao = new AlumnoDAO(ListaAlumnos.this); //context != this -> ListaAlumnos.this
						List<Alumno> alumnos = dao.getLista();
						dao.close();
						
						//implementaremos la clase AlumnoConverter con el metodo toJSON
						//que reciba por parámetro la lista de alumnos
						String datosJSON = new AlumnoConverter().toJSON(alumnos);
						
						// Recibiremos la respuesta del cliente de la clase WebClient
						WebClient cliente = new WebClient(urlServidor);
						final String respuestaJSON = cliente.post(datosJSON); //post de los datos JSON
						
						
						// no se puede hacer un toast en el main desde un thread distinto					
						//Toast.makeText(ListaAlumnos.this, respuestaJSON, Toast.LENGTH_LONG).show();
						
						
						// --> Se incluye en el onPostExecute de EnviaAlumnoTask
						// Entonces, por consola con LOG
						Log.i("retorno de la llamada: ", respuestaJSON);
						
						ListaAlumnos.this.runOnUiThread(new Runnable() {						
							@Override
							public void run() {
								Toast.makeText(ListaAlumnos.this, 
										respuestaJSON, Toast.LENGTH_LONG)
										.show();
							}
						});
					}
				};
				tareaPesada.start(); // delegamos la tarea al Thread "tareaPesada"
				*
				*/
				break;
	
			default:
				break;
		}
        return super.onOptionsItemSelected(item);
    }
    
}
