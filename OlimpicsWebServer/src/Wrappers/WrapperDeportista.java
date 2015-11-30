package Wrappers;

import com.google.gson.annotations.SerializedName;

public class WrapperDeportista {
	
	@SerializedName("nombreDep")
	private String nombreDep;
	@SerializedName("peso")
	private float peso;
	@SerializedName("edad")
	private int edad;
	@SerializedName("altura")
	private float altura;
	@SerializedName("idDep")
	private int idDep;
	@SerializedName("cuentaT")
	private String cuentaT;
		
	public WrapperDeportista() {
	}
	
	public WrapperDeportista(String nombreDep, int edad, float altura, float peso, String cuentaT) {
		this.nombreDep = nombreDep;
		this.edad = edad;
		this.altura = altura;
		this.peso = peso;
		this.cuentaT = cuentaT;
	}
		
	
	public String getNombreDep() {
		return nombreDep;
	}

	public void setNombreDep(String nombreDep) {
		this.nombreDep = nombreDep;
	}
	
	public int getEdad(){
		return edad;
	}

	public void setEdad(int edad){
		this.edad = edad;
	}
	
	public float getAltura(){
		return altura;
	}
	
	public void setAltura(float altura){
		this.altura = altura;
	}
		
	public float getPeso(){
		return peso;
	}
	
	public void setPeso(float peso){
		this.peso = peso;
	}
	
	
	public int getIdDep(){
		return idDep;
	}
	
	public void setIdDep(int idDep){
		this.idDep = idDep;
	}

	public String getTwitter(){
		return cuentaT;
	}
	
	public void setTwitter(String cuentaT){
		this.cuentaT = cuentaT;
	}
	

}
