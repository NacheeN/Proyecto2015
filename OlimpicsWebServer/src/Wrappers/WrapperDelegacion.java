package Wrappers;

import com.google.gson.annotations.SerializedName;

public class WrapperDelegacion {
	
	@SerializedName("nombre")
	private String nombre;
	@SerializedName("password")
	private String password;
		
	public WrapperDelegacion() {
	}
	
	public WrapperDelegacion(String nombre, String password) {
	
		this.nombre = nombre;
		this.password = password;		
	}
		
	public String imprimirBookData(){		
		return (this.nombre+" "+this.password);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}