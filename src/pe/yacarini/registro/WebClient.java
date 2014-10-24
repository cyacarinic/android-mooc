package pe.yacarini.registro;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class WebClient {

	private String urlServidor;

	public WebClient(String urlServidor) {
		this.urlServidor = urlServidor;
	}

	public String post(String datosJSON) {
		try{
			HttpClient cliente = new DefaultHttpClient();
			HttpPost post = new HttpPost(urlServidor);
			
			post.setEntity(new StringEntity(datosJSON));
			post.setHeader("Content-type", "application/json");
			post.setHeader("Accept", "application/json");
			
			HttpResponse response = cliente.execute(post);
			HttpEntity respuesta = response.getEntity();
			
			String respuestaEnJson = 
					EntityUtils.toString(respuesta);
			
			return respuestaEnJson;
		}catch(Exception e){
			//throw new RuntimeException(e);
			return "<Warning : Error de conexión con el servidor/>";
		}
	}

}
