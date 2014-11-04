package pe.yacarini.registro;

import fragment.DetallePruebaFragment;
import fragment.ListaPruebaFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class PruebasActivity extends FragmentActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.pruebas);
		
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction beginTransaction = manager.beginTransaction();
		
		if(isTabletEnHorizontal()){
			beginTransaction.replace(R.id.pruebas_lista, new ListaPruebaFragment());
			beginTransaction.replace(R.id.pruebas_detalle, new DetallePruebaFragment());
		}else{
			beginTransaction.replace(R.id.unico, new ListaPruebaFragment());
		}
		
		beginTransaction.commit();
		
	}

	private boolean isTabletEnHorizontal() {
		// TODO Auto-generated method stub
		return true;
	}
}
