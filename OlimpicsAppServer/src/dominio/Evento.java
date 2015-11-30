package dominio;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "evento")
public class Evento implements Serializable {

	private static final long serialVersionUID = 1L; // Mapping JPA

	@Id
	@Column(name = "Nombre", nullable = false)
	private String nombreE;
				
	@OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Usuario organizador;
	
	
	
	
	public Evento(){}

	public Evento(String nombreE) {
		this.nombreE = nombreE;	
	}


	public Evento(Evento ev) {
		this.nombreE = ev.getNombreE();	
	}	

	public String getNombreE() {
		return nombreE;
	}

	public void setNombreE(String nombreE) {
		this.nombreE = nombreE;
	}
	
	public Usuario getOrganizador() {
		return organizador;
	}

	public void setOrganizador(Usuario organizador) {
		this.organizador = organizador;
	}
	

	
}