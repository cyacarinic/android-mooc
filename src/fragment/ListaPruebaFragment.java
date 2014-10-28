package fragment;


import pe.yacarini.registro.R;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class ListaPruebaFragment extends Fragment{	
	  
	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View layoutPruebas = inflater.inflate(R.layout.pruebas_lista, container,false);
		return layoutPruebas;
	}
}
