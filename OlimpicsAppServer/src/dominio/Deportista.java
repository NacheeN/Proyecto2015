package dominio;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "Deportista")
public class Deportista implements Serializable {

	private static final long serialVersionUID = 1L; // Mapping JPA

	@Id
	@Column(name = "Id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idDep;
	
	@Column(name = "Nombre", nullable = false)
	private String nombreDep;

	@Column(name = "Twitter", nullable = false)
	private String cuentaT;
	
	@Column(name = "Edad", nullable = false)
	private int edad;
	
	@Column(name = "Altura", nullable = false)
	private float altura;
	
	@Column(name = "Peso", nullable = false)
	private float peso;
	
	@JoinColumn(name = "IdDeporte", nullable = true)
	private int idDeporte;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="Pais",nullable = true)
	private Pais pais;
		
	@OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Usuario delegacion;
	
	

	public Deportista() {}

	public Deportista(String nombreDep, int edad, float altura, float peso, String cuentaT) {
		this.nombreDep = nombreDep;
		this.edad = edad;
		this.altura = altura;
		this.peso = peso;		
		this.cuentaT = cuentaT;
	}

	
	public void cargarJugador(String nombre) {
		
		this.nombreDep = nombre;		
	}
	
	

	public Deportista(Deportista dep) {
		this.nombreDep = dep.getNombreDep();
		this.altura = dep.getAltura();
		this.edad = dep.getEdad();
		this.peso = dep.getPeso();		
		this.cuentaT = dep.getTwitter();
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
	
	public Pais getPais() {
		return pais;
	}
	
	public void setPais(Pais pais) {
		this.pais = pais;
	}
	
	
	
	public Usuario getDelegacion() {
		return delegacion;
	}

	public void setDelegacion(Usuario delegacion) {
		this.delegacion = delegacion;
	}
	
}