package dominio;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "Noticia")
public class Noticia implements Serializable {

	private static final long serialVersionUID = 1L; // Mapping JPA

	@Id
	@Column(name = "Id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idNoticia;
	
	@Column(name = "Titulo", nullable = false)
	private String titulo;

	@Column(name = "Descripcion", nullable = false)
	private String desc;
	
	@Column(name = "Tag", nullable = false)
	private String tag;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="Evento",nullable = true)
	private Evento evento;
	
	@OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Usuario publicador;
	
	

	public Noticia() {}

	public Noticia(String titulo, String desc, String tag) {
		this.titulo = titulo;
		this.desc = desc;
		this.tag = tag;
	}


	public Noticia(Noticia noticia) {
		this.titulo = noticia.getTitulo();
		this.desc = noticia.getDescripcion();
		this.tag = noticia.getTag();
				
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
	
	public Evento getEvento(){
		return evento;
	}
	
	public void setEvento(Evento evento){
		this.evento = evento;
	}

	public Usuario getPublicador() {
		return publicador;
	}

	public void setPublicador(Usuario publicador) {
		this.publicador = publicador;
	}
	
}