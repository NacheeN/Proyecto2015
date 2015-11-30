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

import Wrappers.WrapperCompetencia;
import Wrappers.WrapperNoticia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;




@ManagedBean
@javax.faces.bean.SessionScoped
public class CompetenciaMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nombre;
	
////////////////////////////////////////////////////////////
	
	
	public String registroCompetencia() {

		ClientRequest request;
		ClientResponse<String> response;
		

		try {
				
				request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/CompetenciaService/competencia");

				WrapperCompetencia competencia = new WrapperCompetencia(this.nombre);

				String competenciaJSON = toJSONString(competencia);
				System.out.println(competenciaJSON);

				request.body("application/json", competenciaJSON);
				
				response = request.post(String.class);
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Alta de competencia con exito"));                
				FacesContext.getCurrentInstance().getExternalContext().redirect("Index.xhtml");							
		} 
		catch (Exception e) {			
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
public String altaCompetencia(){	
		
		ClientRequest request;
		ClientResponse<String> response;
		
		String nick = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("competencia", this.nombre);	
		System.out.println(nick);
		
		try {						
						
				request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/CompetenciaService/competencia");
				WrapperCompetencia competencia = new WrapperCompetencia(this.nombre);
				
				String competenciaJson = toJSONString(competencia);
				request.header("username",nick);
				request.body("application/json", competenciaJson);
				
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	public String toJSONString(Object object) { 
		
		GsonBuilder gsonBuilder = new GsonBuilder();

		Gson gson = gsonBuilder.create();
		return gson.toJson(object);
	}

	

	
	
	
	
	
	
	
	
	
	
}