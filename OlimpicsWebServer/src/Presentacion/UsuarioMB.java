package Presentacion;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import Wrappers.WrapperUsuario;
import Wrappers.WrapperDelegacion;
import Wrappers.WrapperOrganizador;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;



@ManagedBean
@javax.faces.bean.SessionScoped
public class UsuarioMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nick;
	private String password;
	private String rol;	
	private String link;                                                                                  
	private String nombre;
	
////////////////////////////////////////////////////////////
	
public String Login(){
		
		ClientRequest request;
		  
		try {

			request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/UsuarioService/login");		
			
			WrapperUsuario u = new WrapperUsuario(nick, password);
			
			String userJson = toJSONString(u);			
			request.body("application/json", userJson);		
			
			
			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson = gsonBuilder.create();
			
			ClientResponse<String> response = request.post(String.class);
			
			JsonParser parser = new JsonParser();
		    JsonArray jArray = parser.parse(response.getEntity()).getAsJsonArray();
			
			ArrayList<Boolean> lista = new ArrayList<Boolean>();

			    for(JsonElement obj : jArray)
			    {
			    	Boolean booleano = gson.fromJson(obj , Boolean.class);
			    	lista.add(booleano);		  
			    }
			
			    
			if (lista.get(0) == false) {				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Credenciales incorrectas o Perfil deshabilitado"));
				FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			}	
			
			else{
						
				request.clear();
				
				request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/UsuarioService/rol/"+this.nick);		
				
				ClientResponse<String> respuesta = request.get(String.class);
				
				this.rol = respuesta.getEntity(String.class);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", this.nick);	
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("rol", this.rol);
				
					if("Admin".equals(this.rol)){					
						FacesContext.getCurrentInstance().getExternalContext().redirect("RegistroOrganizador.xhtml");
					}						
					if("Usuario".equals(this.rol)){			
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Bienvenido! "+this.nick));	
					FacesContext.getCurrentInstance().getExternalContext().redirect("Index.xhtml");
					System.out.println("Soy un usuario comun");
					}	
					if("Organizador".equals(this.rol)){			
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Bienvenido! "+this.nick));	
					FacesContext.getCurrentInstance().getExternalContext().redirect("AltaEvento.xhtml");
					System.out.println("Soy un organizador");
					}	
					if("Delegacion".equals(this.rol)) {			
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Bienvenido! "+this.nick));	
					FacesContext.getCurrentInstance().getExternalContext().redirect("AltaDeportist.xhtml");
					System.out.println("Soy una delegacion");
					}	
				}
			
		} 
		catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
		
	return null;

	}
	

//////////////////////////////////////////////////////////////////////////////////////

	public String logOut() {

		try {
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			FacesContext.getCurrentInstance().getExternalContext().redirect("Index.xhtml");
		} 
		catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return null;
	}
	

	@SuppressWarnings("deprecation")
	public String registroUsuario() {

		ClientRequest request;

		try {
			request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/UsuarioService/usuario");
			WrapperUsuario usuario = new WrapperUsuario(this.nick, this.password);
			
			String usuarioJSON = toJSONString(usuario);			
			request.body("application/json", usuarioJSON);			
			ClientResponse<String> response = request.post(String.class);

			if (response.getStatus() != 201) {
				FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Error al ingresar un nuevo usuario"));
				throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
			}

			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Alta de Usuario con exito"));
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			FacesContext.getCurrentInstance().getExternalContext().redirect("Index.xhtml");
		} 
		catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}

		return null;
	}
	
	
	//////////////////////////////////////////////////DELEGACION/////////////////////////////////////////
		
	@SuppressWarnings("deprecation")
	public String registroDelegacion() {

		ClientRequest request;

		try {
			request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/UsuarioService/delegacion");
			WrapperUsuario delegacion = new WrapperUsuario(this.nick, this.password);
			
			String delegacionJSON = toJSONString(delegacion);		
			request.body("application/json", delegacionJSON);			
			ClientResponse<String> response = request.post(String.class);

			if (response.getStatus() != 201) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error al ingresar una nueva delegacion"));
				throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
			}			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Alta de delegacion con exito"));
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			FacesContext.getCurrentInstance().getExternalContext().redirect("Index.xhtml");
		} 
		catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
/////////////////////////////////////////////organizador/////////////////////////////////////////////////////////
	
	@SuppressWarnings("deprecation")
	public String registroOrganizador() {

		ClientRequest request;

		try {
			request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/UsuarioService/organizador");
			WrapperUsuario organizador = new WrapperUsuario(this.nick, this.password);
			
			String organizadorJSON = toJSONString(organizador);		
			request.body("application/json", organizadorJSON);			
			ClientResponse<String> response = request.post(String.class);

			if (response.getStatus() != 201) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error al ingresar un nuevo organizador"));
				throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
			}			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Alta de organizador con exito"));
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			FacesContext.getCurrentInstance().getExternalContext().redirect("Index.xhtml");
		} 
		catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}

		return null;
	}
	
	
	
/////////////////////////////////////////////fin de organizador/////////////////////////////////////////////////////////
	
	
	public String getPassword() {
		return password;
	}

	
	public String getNick() {
		return nick;
	}

	public String getNombre(){
		return nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	
	public String toJSONString(Object object) { // Funcion que convierte de objeto java a JSON
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		return gson.toJson(object);
	}

}
