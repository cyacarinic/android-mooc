package pe.yacarini.registro.servicios;

import pe.yacarini.registro.dao.AlumnoDAO;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.gsm.SmsMessage;
import android.widget.Toast;

import pe.yacarini.registro.R;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// Se puede manejar wifi, nivel de bateria, sms
		Object[] mensajes = (Object[])intent.getExtras().get("pdus");
		byte[] primero = (byte[])mensajes[0];
		
		SmsMessage sms = SmsMessage.createFromPdu(primero);
		String telefono = sms.getDisplayOriginatingAddress();
		
		Toast.makeText(context, "SMS desde telefono "+telefono, 
				Toast.LENGTH_LONG).show();
		
		AlumnoDAO dao = new AlumnoDAO(context);
		boolean esAlumno = dao.isAlumno(telefono);
		dao.close();
		if (esAlumno) {
			MediaPlayer player = MediaPlayer.create(context, R.raw.main);
			player.start();
		}
	}

}
