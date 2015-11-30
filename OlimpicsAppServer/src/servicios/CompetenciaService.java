package servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import controladores.ICompetenciaController;
import dominio.Competencia;
import dominio.Noticia;

@Path("/CompetenciaService")


public class CompetenciaService extends Application {

	@EJB
	private ICompetenciaController icc;


	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/status")	
	public Response getStatus() {
		
		return Response.ok("{\"status\":\"El servicio de los competencias esta funcionando...\"}").build();
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/comp")
	public Response guardarCompetencia(String datos) {
		System.out.println("payload - " + datos);
		boolean creado = false;
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		
		Competencia competencia = gson.fromJson(datos, Competencia.class);

		String returnCode = "200";

	
		try {

			creado = this.icc.guardarCompetencia(competencia.getNombre()); 
					

			returnCode = "{"
					+ "\"href\":\"http://localhost:8080/OlimpicsWebServer/rest/CompetenciaService/competencia/"
					+ competencia.getNombre() + "\","
					+ "\"message\":\"New book successfully created.\"" + "}";

		} catch (Exception err) {
			err.printStackTrace();
			returnCode = "{\"status\":\"500\","
					+ "\"message\":\"Resource not created.\""
					+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(500).entity(returnCode).build();

		}
		return Response.status(201).entity(creado).build();
	}
	
	
	
	
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)	// lo que genera
	@Consumes(MediaType.APPLICATION_JSON)	// lo que recibe
	@Path("/competencia")
	public Response createNewCompetencia(String datos, @HeaderParam("username") String username){
	
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		
		Competencia competencia = gson.fromJson(datos, Competencia.class);
		String returnCode = "200";
		
		String response = "";
		try {			
			List<String> comp= icc.altaCompetencia(competencia, username);
			response = toJSONString(comp);
			returnCode = "{"
			+ "\"href\":\"http://localhost:8080/DeportistaWebService/rest/deportistaservice/deportista/"+competencia.getNombre()+"\","
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