package fragment;

import java.util.Arrays;
import java.util.List;

import pe.yacarini.registro.R;
import pe.yacarini.registro.modelo.Prueba;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaPruebaFragment extends Fragment{	
	  
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View listaDePruebas = inflater.inflate(R.layout.pruebas_lista, null);
		
		ListView listaPruebas = (ListView)listaDePruebas.findViewById(R.id.pruebas);
		
		Prueba prueba1 = new Prueba("16/11/1989","Piura");
		prueba1.setTopicos(Arrays.asList("Spring MVC", "Spring JDBC",
				"Spring JMS"));
		
		Prueba prueba2 = new Prueba("10/01/1989","Lima");
		prueba1.setTopicos(Arrays.asList("Fisioterapia", "Agentes",
				"Reeducación"));
		
		List<Prueba> pruebas = Arrays.asList(prueba1, prueba2);
		
		FragmentActivity context = getActivity();
		
		int layout = android.R.layout.simple_list_item_1;
		ArrayAdapter<Prueba> adapter = new ArrayAdapter<Prueba>(context, 
				layout, pruebas);
		listaPruebas.setAdapter(adapter);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
