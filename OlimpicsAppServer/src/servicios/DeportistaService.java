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

import controladores.IDeportistaController;
import dominio.Deportista;
import dominio.Pais;

@Path("/DeportistaService")

// para mapearlo en la url
public class DeportistaService extends Application {

	@EJB
	private IDeportistaController idc;

	// localhost:8080/JatrickAppServer/rest/DeportistaService/status/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/status")	
	public Response getStatus() {
		
		return Response.ok("{\"status\":\"El servicio de los deportistas esta funcionando...\"}").build();
	}
	
	
	
	// localhost:8080/JatrickAppServer/rest/UsuarioService/usuario
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deportista")
	public Response guardarDeportista(String datos) {
		System.out.println("payload - " + datos);
		boolean creado = false;
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		
		Deportista deportista = gson.fromJson(datos, Deportista.class);

		String returnCode = "200";

		// Guardo el libro
		try {

			// IUsuarioController iuc = Fabrica.getInstance();

			creado = this.idc.guardarDeportista(deportista.getNombreDep(), deportista.getEdad(), deportista.getAltura(), deportista.getPeso(),  deportista.getTwitter()); 
					

			returnCode = "{"
					+ "\"href\":\"http://localhost:8080/OlimpicsWebServer/rest/DepService/deportista/"
					+ deportista.getNombreDep() + "\","
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
	
	
	
	
	
	
////////////////////////////////////////////////////////
	@POST
	@Produces(MediaType.APPLICATION_JSON)	// lo que genera
	@Consumes(MediaType.APPLICATION_JSON)	// lo que recibe
	@Path("/deportist")
	public Response createNewDeportista(String datos, @HeaderParam("username") String username){
	
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		// Obtengo el objeto Equipo pareando los datos que me llegan (JSON string)
		Deportista deportista = gson.fromJson(datos, Deportista.class);
		String returnCode = "200";
		
		String response = "";
		try {			
			List<String> dep= idc.altaDeportista(deportista,username);	
			response = toJSONString(dep);
			returnCode = "{"
			+ "\"href\":\"http://localhost:8080/DeportistaWebService/rest/deportistaservice/deportista/"+deportista.getNombreDep()+"\","
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
		
		
	
	
	///////////////////////////LISTAR TODOS LOS DEPORTISTAS///////////////////////////////////
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/listarDep")	
	public Response getDeportistas() {

		String response = null;
		try {	
			List<Deportista> list = idc.listarDeportistas();
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
	
	
	//////////////////////////////////////////////////////////////////////////////////////////
	
	public String toJSONString(Object object) { 
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		return gson.toJson(object);
	}

	
	
}