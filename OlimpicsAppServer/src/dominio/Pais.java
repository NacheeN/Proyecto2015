package dominio;
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pais")
public class Pais implements Serializable {
	
	
private static final long serialVersionUID = 1L; // Mapping JPA
	
	
	@Id
	@Column(name = "Nombre", nullable = false)
	private String nombrePais;
	
	@OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Usuario delegacion;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pais", fetch = FetchType.LAZY)
	private List<Deportista> deportistas;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="evento")
	private Evento evento;

	public Pais() {
	
	}
	
	public Pais(String nombrePais) {
		this.nombrePais = nombrePais;
	}
	
   public Pais(Pais p) {
	this.nombrePais = p.getNombrePais();
	}

	public String getNombrePais() {
		return nombrePais;
	}
	
	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}
	
	
	public Usuario getDelegacion() {
		return delegacion;
	}
	
	public void setDelegacion(Usuario delegacion) {
		this.delegacion = delegacion;
	}	
	
	
	public List<Deportista> getDeportistas() {
		return deportistas;
	}
	
	public void setDeportistas(List<Deportista> deportistas) {
		this.deportistas = deportistas;
	}
	
	public Evento getEvento() {
		return evento;
	}
	
	public void setEvento(Evento evento) {
		this.evento = evento;
	}

}