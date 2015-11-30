package dominio;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "Competencia")
public class Competencia implements Serializable {

	private static final long serialVersionUID = 1L; // Mapping JPA

	@Id
	@Column(name = "Id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "Nombre", nullable = false)
	private String nombre;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="Evento",nullable = true)
	private Evento evento;
	
	@OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Usuario creador;
	

	public Competencia() {}

	public Competencia(String nombre) {
		this.nombre = nombre;
	}

	public Competencia(Competencia comp) {
		this.nombre = comp.getNombre();
	}	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public Evento getEvento(){
		return evento;
	}
	
	public void setEvento(Evento evento){
		this.evento = evento;
	}

	public Usuario getCreador() {
		return creador;
	}

	public void setCreador(Usuario creador) {
		this.creador = creador;
	}
	
}