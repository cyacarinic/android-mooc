package fragment;

import pe.yacarini.registro.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetallePruebaFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View listaDePruebas = inflater.inflate(R.layout.pruebas_detalle, null);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}

}
