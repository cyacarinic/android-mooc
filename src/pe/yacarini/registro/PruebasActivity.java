package pe.yacarini.registro;

import java.util.Arrays;
import java.util.List;

import pe.yacarini.registro.modelo.Prueba;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PruebasActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.pruebas_lista);
		
		ListView listaDePruebas = (ListView)findViewById(R.id.pruebas);
		
		Prueba prueba1 = new Prueba("16/11/1989","Piura");
		prueba1.setTopicos(Arrays.asList("Spring MVC", "Spring JDBC",
				"Spring JMS"));
		
		Prueba prueba2 = new Prueba("10/01/1989","Lima");
		prueba1.setTopicos(Arrays.asList("Fisioterapia", "Agentes",
				"Reeducación"));
		
		List<Prueba> pruebas = Arrays.asList(prueba1, prueba2);
		
		int layout = android.R.layout.simple_list_item_1;
		ArrayAdapter<Prueba> adapter = new ArrayAdapter<Prueba>(this, 
				layout, pruebas);
		listaDePruebas.setAdapter(adapter);
		
	}
}
