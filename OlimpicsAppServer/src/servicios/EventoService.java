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




import dominio.Deportista;
import dominio.Evento;
import dominio.Noticia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;






import controladores.IEventoController;
import controladores.IUsuarioController;


@Path("/EventoService") // para mapearlo en la url
public class EventoService extends Application {
	
	@EJB
	private IEventoController iec;
	@EJB
	private IUsuarioController iuc;	
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/status")
	// para mapearlo en la url
	public Response getStatus() {
		return Response.ok("{\"status\":\"El servicio de los eventos esta funcionando...\"}").build();
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/eventos")		
	public Response getEventos(){
		
		
		System.out.println("estoy en eventos del servicio");
		String response = null;
		try {

			List<Evento> list = iec.listarEventos();		
			response = toJSONString(list);
						
		} catch (Exception err) {
			response = "{\"status\":\"401\","
					+ "\"message\":\"No content found \""
					+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(401).entity(response).build();
		}
		return Response.ok(response).build();		
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/evento/{name}")	
	public Response getEvento(@PathParam("name") String nombreE){
		String response = null;
		try {
			
			Evento existingEvento = iec.buscarEvento(nombreE);
			
			if(null == existingEvento){
				response = "{\"status\":\"401\","+ "\"message\":\"No content found \""
						+ "\"developerMessage\":\"Equipo - "+nombreE+" Not Found " + "}";
				return Response.status(401).entity(response).build();
			}			
			response = toJSONString(existingEvento);
		} 
		catch (Exception err) {
			response = "{\"status\":\"401\","+ "\"message\":\"No content found \""
					+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(401).entity(response).build();
		}
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
		
	}
	//////////////////////////Trae true si el equipo ya fue ingresado//////////////////////////////////
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/existeEvento/{name}")	
	public Response existeEvento(@PathParam("name") String nombreE){
		String returnCode = "200";
		String response = null;
		boolean existe = false;
		try {
			
			existe = iec.existeEvento(nombreE);					
		} 
		catch (Exception err) {
			err.printStackTrace();		
			returnCode = "{\"status\":\"500\","+"\"message\":\"Resource not created.\""+
					"\"developerMessage\":\""+err.getMessage()+"\""+"}";
			return  Response.status(500).entity(returnCode).build();
		}
		return  Response.status(201).entity(existe).build(); 		
	}
	
	////////////////////////////////////////////////////////
	@POST
	@Produces(MediaType.APPLICATION_JSON)	// lo que genera
	@Consumes(MediaType.APPLICATION_JSON)	// lo que recibe
	@Path("/evento")
	public Response createNewEvento(String datos, @HeaderParam("username") String username){
	
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		Evento evento = gson.fromJson(datos, Evento.class);
		String returnCode = "200";		
		String response = "";
		try {
			
			List<String> zona= iec.altaEvento(evento, username);
			response = toJSONString(zona);
			returnCode = "{"
					+ "\"href\":\"http://localhost:8080/EquipoWebService/rest/equiposervice/equipo/"+evento.getNombreE()+"\","
					+ "\"message\":\"New equipo successfully created.\""+ "}";
		} 
		catch (Exception err) {
			err.printStackTrace();
			returnCode = "{\"status\":\"500\","+"\"message\":\"Resource not created.\""+
					"\"developerMessage\":\""+err.getMessage()+"\""+"}";
			return  Response.status(500).entity(returnCode).build(); 

		}
		return Response.ok(response).build();
	}
	


	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/evento/{name}")
	public Response deleteEvento(@PathParam("name") String nombreE) {
		
		String returnCode = "";

		try {
			Evento evento = iec.buscarEvento(nombreE);
			iec.borrarEvento(evento);
			
			returnCode = "{"+ "\"message\":\"Evento succesfully deleted\""+ "}";
		} 
		catch (Exception err) {
			err.printStackTrace();
			returnCode = "{\"status\":\"500\","+"\"message\":\"Resource not deleted.\""+
					"\"developerMessage\":\""+err.getMessage()+"\""+"}";
			return  Response.status(500).entity(returnCode).build(); 
		}
		return Response.ok(returnCode,MediaType.APPLICATION_JSON).build();
	}
	
	
	
	
	
//////////////////////////////////////FUNCIONES DE NOTICIAS////////////////////////////////////////////////////
	@POST
	@Produces(MediaType.APPLICATION_JSON)	// lo que genera
	@Consumes(MediaType.APPLICATION_JSON)	// lo que recibe
	@Path("/noticia")
	public Response createNewNoticia(String datos, @HeaderParam("username") String username){
	
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		
		Noticia noticia = gson.fromJson(datos, Noticia.class);
		String returnCode = "200";
		
		String response = "";
		try {			
			List<String> not= iec.altaNoticia(noticia, username);
			response = toJSONString(not);
			returnCode = "{"
			+ "\"href\":\"http://localhost:8080/DeportistaWebService/rest/deportistaservice/deportista/"+noticia.getTitulo()+"\","
			+ "\"message\":\"New deportista successfully created.\""
			+ "}";
		} 
		catch (Exception err) {
			err.printStackTrace();
			returnCode = "{\"status\":\"500\","+
			"\"message\":\"Resource not created.\""+
			"\"developerMessage\":\""+err.getMessage()+"\""+
			"}";
			return  Response.status(500).entity(returnCode).build(); 
		
		}
		return Response.ok(response).build();
	}

	
	public String toJSONString(Object object) {	
		GsonBuilder gsonBuilder = new GsonBuilder();	
		Gson gson = gsonBuilder.create();
		return gson.toJson(object);
	}
	
}


	
	
