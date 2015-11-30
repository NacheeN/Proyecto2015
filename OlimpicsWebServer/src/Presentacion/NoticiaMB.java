package Presentacion;

import java.io.IOException;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import Wrappers.WrapperNoticia;

@ManagedBean
@javax.faces.bean.ViewScoped
public class NoticiaMB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String descripcion;
	private String titulo;
	private String tag;
	private List<WrapperNoticia> noticias;

	
	@PostConstruct
	public void loadNoticias(){
		
		
		ClientRequest request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/UsuarioService/getNoticias/");
		
		List<WrapperNoticia> lwNot = new ArrayList<WrapperNoticia>();

		try {

			request.accept("application/json");
			ClientResponse<String> response;

			response = request.get(String.class);

			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson = gsonBuilder.create();

			JsonParser parser = new JsonParser();
	
			JsonArray jArray = parser.parse(response.getEntity()).getAsJsonArray();

			for (JsonElement Noticia : jArray) {
				WrapperNoticia wnot = new WrapperNoticia();
				wnot = gson.fromJson(Noticia, WrapperNoticia.class);
				lwNot.add(wnot);
	
						
				System.out.println(lwNot);
			
			}
	
			this.noticias = lwNot;
		
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}

	
	
	
	public List<WrapperNoticia> getNoticias() {
		return noticias;
	}


	public void setNoticiass(ArrayList<WrapperNoticia> noticias) {
		this.noticias = noticias;
	}
	
	
	public String irNuevaNoticia() throws IOException{
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("CrearMensaje.xhtml");
		return null;
		
	}
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public String getTag() {
		return tag;
	}
	
	
	public void irMisNoticias() throws IOException{
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("Noticias.xhtml");
		
	}
	
public String toJSONString(Object object) { 
	
	GsonBuilder gsonBuilder = new GsonBuilder();
	Gson gson = gsonBuilder.create();
	return gson.toJson(object);
}

}
