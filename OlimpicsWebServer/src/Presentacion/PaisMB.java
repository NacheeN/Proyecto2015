package Presentacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import utilidades.DeportistaAdapter;
import Wrappers.WrapperPais;
import Wrappers.WrapperDeportista;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


@ManagedBean
@javax.faces.bean.SessionScoped
public class PaisMB implements Serializable {
	
	
	  private static final long serialVersionUID = 1L;	    
 
	  private String nombrePais;
	  
	  private WrapperDeportista selectedDeportista;
	  private List<WrapperDeportista> misDeportistas = new ArrayList<WrapperDeportista>();
	  private List<WrapperDeportista> selectedDeportistas;
///////////////////////////////*****Alta Equipo******//////////////////////////// 
	  
			public String altaPais()
			{	
				
				String existePais;
				ClientRequest request;
				ClientResponse<String> response;
				
				String nick = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
				
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("pais", this.nombrePais);	
				
				
				try {
					request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/PaisService/existePais/"+this.nombrePais);
					response = request.get(String.class);					
					existePais= response.getEntity(String.class);	
					
					if (existePais.equals("true")) {
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("El pais ya existe"));
						throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
							
					}
					else{
						request.clear();
						
						request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/PaisService/pais");
						WrapperPais pais = new WrapperPais(this.nombrePais);
						
						String paisJson = toJSONString(pais);
						request.header("username",nick);
						request.body("application/json", paisJson);
						
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
					
						System.out.println("Estoy en pais MB");
						FacesContext.getCurrentInstance().getExternalContext().redirect("Index.xhtml");						
					}					
				} 
				catch (Exception e) {
					e.printStackTrace();
				}				
				return null;				
			}
			
			
			

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			public String darPaises(){		
						
				
				ClientRequest request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/PaisService/paises");
				request.accept("application/json");
				ClientResponse<String> response;
				
				
				try {
				
					response = request.get(String.class);
					GsonBuilder gsonBuilder = new GsonBuilder();
					Gson gson = gsonBuilder.create();
					
					    JsonParser parser = new JsonParser();
					    JsonArray jArray = parser.parse(response.getEntity()).getAsJsonArray();

					    ArrayList<WrapperPais> lcs = new ArrayList<WrapperPais>();

					    for(JsonElement obj : jArray )
					    {
					    	WrapperPais cse = gson.fromJson( obj , WrapperPais.class);
					        lcs.add(cse);					      
					    }					
				} 
				catch (Exception e) {					 
					e.printStackTrace();
				}				
				return null;
			}
			
///////////////////////////////////////////////////////Lista jugadores del equipo a Vender////////////////
			public List<WrapperDeportista> darDeportistas(){		
				System.out.println("entro dar Jugadores");
				String pais = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pais");
				System.out.println("entro dar Jugadoresjjkhkjhjkh"+pais );
				ClientRequest request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/PaisService/deportistas/"+ pais);
				
				ArrayList<WrapperDeportista> lwj = new ArrayList<WrapperDeportista>();
				
				try {
					request.accept("application/json");
					System.out.println("entro dar Jugadores nombre equipo" + pais);
					
					ClientResponse<String> response = request.get(String.class);
					GsonBuilder gsonBuilder = new GsonBuilder();
					
					///////////////////////
					gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					Gson gson = gsonBuilder.create();
					
					JsonParser parser = new JsonParser();
					JsonArray jArray = parser.parse(response.getEntity()).getAsJsonArray();
					
					for (JsonElement Deportista : jArray) {
						WrapperDeportista wj = new WrapperDeportista();
						wj = gson.fromJson(Deportista, WrapperDeportista.class);
						
						lwj.add(wj);
					
					}
					this.misDeportistas = lwj;
					FacesContext.getCurrentInstance().getExternalContext().redirect("Venta.xhtml");				
				} 
				catch (Exception e) {				
					e.printStackTrace();
				}				
				return null;
			}
			
/////////////////////////////////////////////////////////////////////////
			
			public List<WrapperDeportista> darDeportistasMiPais(){		
				
				String pais = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pais");
				ClientRequest request = new ClientRequest("http://localhost:8080/OlimpicsAppServer/rest/PaisService/deportistas/"+ pais);
				
				ArrayList<WrapperDeportista> lwj = new ArrayList<WrapperDeportista>();
				
				try {
					request.accept("application/json");				
					
					ClientResponse<String> response = request.get(String.class);
					GsonBuilder gsonBuilder = new GsonBuilder();
					Gson gson = gsonBuilder.create();
					
					JsonParser parser = new JsonParser();
					JsonArray jArray = parser.parse(response.getEntity()).getAsJsonArray();
					
					for (JsonElement Jugador : jArray) {
						WrapperDeportista wj = new WrapperDeportista();
						wj = gson.fromJson(Jugador, WrapperDeportista.class);
						
						lwj.add(wj);
					
					}
					this.misDeportistas = lwj;
								
				

					FacesContext.getCurrentInstance().getExternalContext().redirect("MiPais.xhtml");		
				
				} catch (Exception e) {
				
					e.printStackTrace();
				}
				
				
				return null;
			}

////////////////////////////////////////////////////////////////////////////////////////////
			
			public String getNombrePais() {
				return nombrePais;
			}
			public void setNombrePais(String nombrePais) {
				this.nombrePais = nombrePais;
			}			
									
			public List<WrapperDeportista> getMisDeportistas() {
				return misDeportistas;
			}
			
			public void setMisDeportistas(List<WrapperDeportista> misDeportistas) {
				this.misDeportistas = misDeportistas;
			}
		
			public WrapperDeportista getSelectedDeportista() {			        
				return selectedDeportista;			    
			}			 
			    
			public void setSelectedDeportista(WrapperDeportista selectedDeportista) {			        
				this.selectedDeportista = selectedDeportista;			    
			}			 
			    
			public List<WrapperDeportista> getSelectedDeportistas() {			        
				return selectedDeportistas;			    
			}
			 			
			public void setSelectedJugadores(List<WrapperDeportista> selectedDeportistas) {			        
				this.selectedDeportistas = selectedDeportistas;			    
			}
			
			
			public String toJSONString(Object object) {	//	Funcion que convierte de objeto java a JSON
				GsonBuilder gsonBuilder = new GsonBuilder();
				Gson gson = gsonBuilder.create();
				return gson.toJson(object);
			}
			
			public String deportistasToJSONString(List<WrapperDeportista> deportistas) {  
			    GsonBuilder gsonBuilder = new GsonBuilder();
			    Gson gson = gsonBuilder.registerTypeAdapter(WrapperDeportista.class, new DeportistaAdapter()).create();
			    return gson.toJson(deportistas);
			}  

}
