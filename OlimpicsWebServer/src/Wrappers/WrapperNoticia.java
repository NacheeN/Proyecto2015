package Wrappers;

import com.google.gson.annotations.SerializedName;

public class WrapperNoticia {
	
	@SerializedName("titulo")
	private String titulo;
	@SerializedName("desc")
	private String desc;
	@SerializedName("idNoticia")
	private int idNoticia;
	@SerializedName("tag")
	private String tag;
		
	public WrapperNoticia() {
	}
	
	public WrapperNoticia(String titulo, String desc, String tag) {
	
		this.titulo = titulo;
		this.desc = desc;	
		this.tag = tag;
	}
		
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getDescripcion() {
		return desc;
	}

	public void setDescripcion(String desc) {
		this.desc = desc;
	}
	
	public int getIdNoticia(){
		return idNoticia;
	}
	
	public void setIdNoticia(int idNoticia){
		this.idNoticia = idNoticia;
	}

	public String getTag(){
		return tag;
	}
	
	public void setTag(String tag){
		this.tag = tag;
	}
	
}