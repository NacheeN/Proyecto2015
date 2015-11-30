package utilidades;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dominio.Deportista;

public class DeportistaAdapter implements JsonSerializer<Deportista> {

	@Override
	public JsonElement serialize(Deportista deportista, Type type,JsonSerializationContext jsc) {

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("nombreDep", deportista.getNombreDep());		
		jsonObject.addProperty("cuentaT", deportista.getTwitter());
		jsonObject.addProperty("altura", deportista.getAltura());
		jsonObject.addProperty("edad", deportista.getEdad());
		jsonObject.addProperty("peso", deportista.getPeso());
	
		
		return jsonObject;
	}

}
