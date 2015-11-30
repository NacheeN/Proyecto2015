package Wrappers;

import com.google.gson.annotations.SerializedName;

public class WrapperCompetencia {
	
	@SerializedName("nombre")
	private String nombre;
	
		
	public WrapperCompetencia() {
	}
	
	public WrapperCompetencia(String nombre) {
		this.nombre = nombre;

	}
		
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
