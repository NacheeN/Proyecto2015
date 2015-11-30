package Wrappers;

import com.google.gson.annotations.SerializedName;

public class WrapperPais {
	
	
	@SerializedName("nombrePais")
	private String nombrePais;
	
	
	
	public WrapperPais(){
		
	}
	
	public WrapperPais(String nombrePais){	
		this.nombrePais =nombrePais;
	
	}
	
	
	public String getNombrePais() {
		return nombrePais;
	}

	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}


}
