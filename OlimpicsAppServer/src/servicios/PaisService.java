package servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import utilidades.DeportistaAdapter;
import dominio.Pais;

import dominio.Deportista;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import controladores.IPaisController;
import controladores.IUsuarioController;


@Path("/PaisService") // para mapearlo en la url
public class PaisService extends Application {
	
	@EJB
	private IPaisController ipc;
	@EJB
	private IUsuarioController iuc;
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/status")
	public Response getStatus() {
		return Response.ok("{\"status\":\"El servicio de los paises esta funcionando...\"}").build();
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/paises")		
	public Response getPaises(){
		
		String response = null;
		try {
			List<Pais> list = ipc.listarPaises();		
			response = toJSONString(list);			
		} 
		catch (Exception err) {
			response = "{\"status\":\"401\","
					+ "\"message\":\"No content found \""
					+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(401).entity(response).build();
		}
		return Response.ok(response).build();		
	}
	
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/pais/{name}")	
	public Response getPais(@PathParam("name") String paisNombre){
		
		String response = null;
		try {			
			Pais existingPais = ipc.buscarPais(paisNombre);			
			if(null == existingPais){
				response = "{\"status\":\"401\"," + "\"message\":\"No content found \""
						+ "\"developerMessage\":\"Equipo - "+paisNombre+" Not Found " + "}";
				return Response.status(401).entity(response).build();
			}			
			response = toJSONString(existingPais);
		} 
		catch (Exception err) {
			response = "{\"status\":\"401\"," + "\"message\":\"No content found \""
			+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(401).entity(response).build();
		}
		return Response.ok(response, MediaType.APPLICATION_JSON).build();		
	}
	//////////////////////////Trae true si el equipo ya fue ingresado//////////////////////////////////
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/existePais/{name}")	
	public Response existePais(@PathParam("name") String paisNombre){
		String returnCode = "200";
		String response = null;
		boolean existe = false;
		try {
			
			existe = ipc.existePais(paisNombre);		
			
		} catch (Exception err) {
			err.printStackTrace();		
			returnCode = "{\"status\":\"500\","+
					"\"message\":\"Resource not created.\""+
					"\"developerMessage\":\""+err.getMessage()+"\""+
					"}";
			return  Response.status(500).entity(returnCode).build(); 

		}
		return  Response.status(201).entity(existe).build(); 
		
	}
	
	////////////////////////////////////////////////////////
	@POST
	@Produces(MediaType.APPLICATION_JSON)	// lo que genera
	@Consumes(MediaType.APPLICATION_JSON)	// lo que recibe
	@Path("/pais")
	public Response createNewPais(String datos, @HeaderParam("username") String username){
	
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		// Obtengo el objeto Equipo pareando los datos que me llegan (JSON string)
		Pais pais = gson.fromJson(datos, Pais.class);
		String returnCode = "200";
		
		String response = "";
		try {
			//System.out.println("EQUIPO SERVICE "+equipo.getNombreE());
			List<String> zona= ipc.altaPais(pais,username);	
			response = toJSONString(zona);
			returnCode = "{"
					+ "\"href\":\"http://localhost:8080/EquipoWebService/rest/equiposervice/equipo/"+pais.getNombrePais()+"\","
					+ "\"message\":\"New equipo successfully created.\""
					+ "}";
		} catch (Exception err) {
			err.printStackTrace();
			returnCode = "{\"status\":\"500\","+
					"\"message\":\"Resource not created.\""+
					"\"developerMessage\":\""+err.getMessage()+"\""+
					"}";
			return  Response.status(500).entity(returnCode).build(); 

		}
		return Response.ok(response).build();
	}
	
	
	
	
///////////////////////////////////////////Compra Y Venta Jugadores//////////////////////////////////////
///////////////Lista los jugadores del equipo pasado//////////////////////////////
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deportistas/{name}")	
	public Response getDeportistas(@PathParam("name") String paisNombre){
		String response = null;
		try {
		
			List<Deportista> deportistas = ipc.misDeportistas(paisNombre);
			response = deportistasToJSONString(deportistas);
		
		} catch (Exception err) {
			response = "{\"status\":\"401\","
			+ "\"message\":\"No content found \""
			+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(401).entity(response).build();
		}
		return Response.ok(response).build();
		
		
	}


	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/pais/{name}")
	public Response deletePais(@PathParam("name") String nombreP) {
		
		String returnCode = "";

		try {
			Pais pais = ipc.buscarPais(nombreP);
			ipc.borrarPais(pais);
			
			returnCode = "{"
					+ "\"message\":\"Equipo succesfully deleted\""
					+ "}";
		} catch (Exception err) {
			err.printStackTrace();
			returnCode = "{\"status\":\"500\","+
					"\"message\":\"Resource not deleted.\""+
					"\"developerMessage\":\""+err.getMessage()+"\""+
					"}";
			return  Response.status(500).entity(returnCode).build(); 
		}
		return Response.ok(returnCode,MediaType.APPLICATION_JSON).build();
	}
	
	
	
	public String toJSONString(Object object) {	
		GsonBuilder gsonBuilder = new GsonBuilder();	
		Gson gson = gsonBuilder.create();
		return gson.toJson(object);
	}
	
	public String deportistasToJSONString(List<Deportista> deportistas) {  
	    GsonBuilder gsonBuilder = new GsonBuilder();
	    Gson gson = gsonBuilder.registerTypeAdapter(Deportista.class, new DeportistaAdapter()).create();
	    return gson.toJson(deportistas);
	} 
	
	 
	

}