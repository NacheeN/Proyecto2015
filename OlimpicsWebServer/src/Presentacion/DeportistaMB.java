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
import Wrappers.WrapperPais;
import Wrappers.WrapperUsuario;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;




@ManagedBean
@javax.faces.bean.SessionScoped
public class DeportistaMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombreDep;
	private float peso;
	private float altura;	
	private int idDep;
	private int edad;
	private String cuentaT;
	
	private String password;
	private String nick;
	private String rol;
	
	private List<WrapperDeportista> listaDeportistas = new ArrayList<WrapperDeportista>();
////////////////////////////////////////////////////////////
	
	
	public String registroDeportista() {

		ClientRequest request;
		ClientResponse<String> response;		

		try {				
				request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/DeportistaService/deportista");

				WrapperDeportista deportista = new WrapperDeportista(this.nombreDep, this.edad, this.altura, this.peso,  this.cuentaT);
						
				String deportistaJSON = toJSONString(deportista);
				System.out.println(deportistaJSON);

				request.body("application/json", deportistaJSON);
				
				response = request.post(String.class);
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Alta de deportista con exito"));                
				FacesContext.getCurrentInstance().getExternalContext().redirect("Index.xhtml");	
				
		} 
		catch (Exception e) {			
			e.printStackTrace();
		}

		return null;

	}
	
	

	
	@SuppressWarnings("deprecation")
	public String registroDeportist() {

		ClientRequest request;

		try {
			request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/UsuarioService/usuario");
			WrapperUsuario usuario = new WrapperUsuario(this.nick, this.password);

			// transformo el usuario a ingresar en Json string
			String usuarioJSON = toJSONString(usuario);

			// Seteo el objeto usuario al body del request
			request.body("application/json", usuarioJSON);

			// se obtiene una respuesta por parte del webService
			ClientResponse<String> response = request.post(String.class);

			if (response.getStatus() != 201) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Error al ingresar un nuevo usuario"));
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Alta de Usuario con exito"));
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			FacesContext.getCurrentInstance().getExternalContext().redirect("Index.xhtml");
			

		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}

		return null;
	}
	
	
	
	
	
	
	
	
	
	
	/////////////////////////////////ALTA DE DEPORTISTA/////////////////////////////////
	//Esta es la última alta correcta, setea el nombre de la delegacion al deportista///
	
	
	public String altaDeportist(){	
		
		ClientRequest request;
		ClientResponse<String> response;
		
		String nick = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("deportista", this.nombreDep);	
		System.out.println(nick);
		
		try {						
						
				request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/DeportistaService/deportist");
				WrapperDeportista deportista = new WrapperDeportista(this.nombreDep, this.edad, this.altura, this.peso, this.cuentaT);
				
				String deportistJson = toJSONString(deportista);
				request.header("username",nick);
				request.body("application/json", deportistJson);
				
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
	
	
	/////////////////////////////////////LISTAR A LOS DEPORTISTAS///////////////////////////////
	
	/*public List<WrapperDeportista> misDeportistas() {
		
		System.out.println("Entre Deportista managed Bean ");
		
		ClientRequest request = new ClientRequest(
				"http://localhost:8080/OlimpicsAppServer/rest/DeportistaService/listarDep");
		ArrayList<WrapperDeportista> listaWDep = new ArrayList<WrapperDeportista>();

		try {

			request.accept("application/json");
			ClientResponse<String> response;

			response = request.get(String.class);

			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson = gsonBuilder.create();
			
			JsonParser parser = new JsonParser();
			JsonArray jArray = parser.parse(response.getEntity()).getAsJsonArray();

			for (JsonElement deportista : jArray) {
				WrapperDeportista wrapDep = new WrapperDeportista();
				wrapDep = gson.fromJson(deportista, WrapperDeportista.class);
				listaWDep.add(wrapDep);			
			}
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("miLiga.xhtml");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;		
	}
	*/
	//////////////////ATRIBUTOS DE WRAPPERDEPORTISTA/////////////////
	
	
	public String getNombreDep() {
		return nombreDep;
	}

	public void setNombreDep(String nombreDep) {
		this.nombreDep = nombreDep;
	}
	
	public int getEdad(){
		return edad;
	}

	public void setEdad(int edad){
		this.edad = edad;
	}
	
	public float getAltura(){
		return altura;
	}
	
	public void setAltura(float altura){
		this.altura = altura;
	}
		
	public float getPeso(){
		return peso;
	}
	
	public void setPeso(float peso){
		this.peso = peso;
	}
	
	public int getIdDep(){
		return idDep;
	}
	
	public void setIdDep(int idDep){
		this.idDep = idDep;
	}
	
	public String getTwitter(){
		return cuentaT;
	}
	
	public void setTwitter(String cuentaT){
		this.cuentaT = cuentaT;
	}
	
	
	//////////////////ATRIBUTOS DE WRAPPERUSUARIO//////////////////////
	
	public String getPassword() {
		return password;
	}

	
	public String getNick() {
		return nick;
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
	
	////////////////////////////////////////////////////////
	
	public String toJSONString(Object object) { 
		
		GsonBuilder gsonBuilder = new GsonBuilder();		
		Gson gson = gsonBuilder.create();
		return gson.toJson(object);
	}
	
	
	
	
	
	
	
	
	
	public String altaDeportista()
	{	
		
		String existeDep;
		ClientRequest request;
		ClientResponse<String> response;
		
		String nick = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("deportista", this.nombreDep);	
		
		
		try {
			request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/DeportistaService/existeDeportista/"+this.nombreDep);
			response = request.get(String.class);					
			existeDep= response.getEntity(String.class);	
			
			if (existeDep.equals("true")) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("El deportista ya existe"));
				throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
					
			}
			else{
				request.clear();
				
				request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/PaisService/pais");
				WrapperDeportista deportista = new WrapperDeportista(this.nombreDep, this.edad, this.peso, this.altura, this.cuentaT);
				
				String deportistaJson = toJSONString(deportista);
				request.header("username",nick);
				request.body("application/json", deportistaJson);
				
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
			
				System.out.println("Estoy en deportista MB");
				FacesContext.getCurrentInstance().getExternalContext().redirect("Index.xhtml");						
			}					
		} 
		catch (Exception e) {
			e.printStackTrace();
		}				
		return null;				
	}
	
	
	
}