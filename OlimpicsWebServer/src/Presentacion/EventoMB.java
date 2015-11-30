package Presentacion;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import Wrappers.WrapperDeportista;
import Wrappers.WrapperEvento;
import Wrappers.WrapperNoticia;
import Wrappers.WrapperPais;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;



@ManagedBean
@javax.faces.bean.SessionScoped
public class EventoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombreE;

	private int idNoticia;
	private String titulo;
	private String desc;
	private String tag;
	
	private List<WrapperEvento> eventos = new ArrayList<WrapperEvento>();
	
////////////////////////////////////////////////////////////
	
	public String altaEvento()
	{	
		
		String existeEvento;
		ClientRequest request;
		ClientResponse<String> response;
		
		String nick = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("evento", this.nombreE);	
		
		
		try {
			request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/EventoService/existeEvento/"+this.nombreE);
			response = request.get(String.class);					
			existeEvento= response.getEntity(String.class);	
			
			if (existeEvento.equals("true")) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("El evento ya existe"));
				throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
					
			}
			else{
				
				request.clear();
				request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/EventoService/evento");
				WrapperEvento evento = new WrapperEvento(this.nombreE);
				
				String eventoJson = toJSONString(evento);
				request.header("username",nick);
				request.body("application/json", eventoJson);
				
				GsonBuilder gsonBuilder = new GsonBuilder();
				Gson gson = gsonBuilder.create();
				
				response = request.post(String.class);
									
				JsonParser parser = new JsonParser();
				JsonArray jArray = parser.parse(response.getEntity()).getAsJsonArray();
				
				ArrayList<String> zona = new ArrayList<String>();
				
				 for(JsonElement obj : jArray)
				 {
				    	String elem = gson.fromJson(obj , String.class);
				    	zona.add(elem);							     
				 }									
			
				System.out.println("Estoy en evento MB");
				FacesContext.getCurrentInstance().getExternalContext().redirect("Index.xhtml");						
			}					
		} 
		catch (Exception e) {
			e.printStackTrace();
		}				
		return null;				
	}
	
	
	
	@SuppressWarnings("deprecation")
	public String guardarEvento() {

		ClientRequest request;

		try {
			request = new ClientRequest(
					"http://localhost:8080/OlimpicsAppServer/rest/EventoService/evento");
			WrapperEvento evento = new WrapperEvento(this.nombreE);

			// transformo el evento a ingresar en Json string
			String eventoJSON = toJSONString(evento);

			// Seteo el objeto evento al body del request
			request.body("application/json", eventoJSON);

			// se obtiene una respuesta por parte del webService
			ClientResponse<String> response = request.post(String.class);

			if (response.getStatus() != 201) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error al ingresar un nuevo evento"));
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			// System.out.println(response.getEntity());
			// System.out.println(response.getStatus());

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Alta de evento con exito"));
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			FacesContext.getCurrentInstance().getExternalContext().redirect("Index.xhtml");
			// return "Index";

		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}

		return null;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////
	
	public List<WrapperEvento> miEvento() {
		System.out.println("Entre Liga managed Bean ");

		ClientRequest request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/EventoService/eventos");
		ArrayList<WrapperEvento> listaWE = new ArrayList<WrapperEvento>();

		try {

			request.accept("application/json");
			ClientResponse<String> response;
			response = request.get(String.class);
			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson = gsonBuilder.create();

			JsonParser parser = new JsonParser();
	
			JsonArray jArray = parser.parse(response.getEntity()).getAsJsonArray();

			for (JsonElement evento : jArray) {
				WrapperEvento wevento = new WrapperEvento();
				wevento = gson.fromJson(evento, WrapperEvento.class);	
				listaWE.add(wevento);
				System.out.println(listaWE);			
			}
			
			this.eventos = listaWE;
			FacesContext.getCurrentInstance().getExternalContext().redirect("miEvento.xhtml");

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	
	}

	
	
	
	
/////////////////////////////////////////////NOTICIAS/////////////////////////////////////////////////
	
	public String altaNoticia(){	
		
		ClientRequest request;
		ClientResponse<String> response;
		
		String nick = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("noticia", this.titulo);	
		System.out.println(nick);
		
		try {						
						
				request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/EventoService/noticia");
				WrapperNoticia noticia = new WrapperNoticia(this.titulo, this.desc, this.tag);
				
				String noticiaJson = toJSONString(noticia);
				request.header("username",nick);
				request.body("application/json", noticiaJson);
				
				GsonBuilder gsonBuilder = new GsonBuilder();
				Gson gson = gsonBuilder.create();
				
				response = request.post(String.class);				
				JsonParser parser = new JsonParser();
				
				FacesContext.getCurrentInstance().getExternalContext().redirect("Index.xhtml");			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("deprecation")
	public String registroNoticia() {
	
			ClientRequest request;
	
			try {
				request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/EventoService/noticia");
				WrapperNoticia noticia = new WrapperNoticia(this.titulo, this.desc, this.tag);
				
				String noticiaJSON = toJSONString(noticia);		
				request.body("application/json", noticiaJSON);			
				ClientResponse<String> response = request.post(String.class);
				
				if (response.getStatus() != 201) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error al ingresar una nueva noticia"));
				throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
				}
				
				
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Alta de noticia con exito"));
				FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
				FacesContext.getCurrentInstance().getExternalContext().redirect("Index.xhtml");
				
			} 
			catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
				e.printStackTrace();
			}
		
		return null;
	}


	
	
	
	public String getNombreE() {
		return nombreE;
	}
	
	public void setNombreE(String nombreE) {
		this.nombreE = nombreE;
	}

	
	public List<WrapperEvento> getEventos() {
		return eventos;
	}
	
	public void setEventos(List<WrapperEvento> eventos) {
		this.eventos = eventos;
	}
	

	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getDescripcion() {
		return desc;
	}
	
	public void setDescripcion(String desc) {
		this.desc = desc;
	}
	
	public int getIdNoticia(){
		return idNoticia;
	}
	
	public void setIdNoticia(int idNoticia){
		this.idNoticia = idNoticia;
	}
	
	public String getTag(){
		return tag;
	}
	
	public void setTag(String tag){
		this.tag = tag;
	}

/////////////////////////////////////////////FIN DE NOTICIAS//////////////////////////////////////////
	

	
	
		
	public String toJSONString(Object object) { 
		
		GsonBuilder gsonBuilder = new GsonBuilder();		
		Gson gson = gsonBuilder.create();
		return gson.toJson(object);
	}

}