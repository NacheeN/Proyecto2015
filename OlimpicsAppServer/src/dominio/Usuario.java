package dominio;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L; // Mapping JPA

	@Id
	@Column(name = "Nick", nullable = false)
	private String nick;

	@Column(name = "Password", nullable = false)
	private String password;
	
	@ManyToOne
	@JoinColumn(name="Roles")
	private Roles roles;
	
	@OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Deportista deportistaFavorito;
	
	@OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Pais paisFavorito;
	
	@OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Pais pais;
	
	@OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Deportista deportista;
	
	@OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Evento evento;
	
	@OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Noticia noticia;
	
	@OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Competencia competencia;
	

	public Usuario() {}

	public Usuario(String nick, String password) {
		this.nick = nick;
		this.password = password;		
	}


	public Usuario(Usuario p) {
		this.nick = p.getNick();
		this.password = p.getPassword();		
	}	

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

		
	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}	
	
	public Deportista getDeportista() {
		return deportista;
	}

	public void setDeportista(Deportista deportista) {
		this.deportista = deportista;
	}	
	
	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
	public Noticia getNoticia() {
		return noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}
	
	public Competencia getCompetencia() {
		return competencia;
	}

	public void setCompetencia(Competencia competencia) {
		this.competencia = competencia;
	}
	
	public Pais getPaisFavorito() {
		return paisFavorito;
	}

	public void setPaisFavorito(Pais paisFavorito) {
		this.paisFavorito = paisFavorito;
	}
	
	public Deportista getDeportistaFav() {
		return deportistaFavorito;
	}

	public void setDeportistaFav(Deportista deportistaFavorito) {
		this.deportistaFavorito = deportistaFavorito;
	}	
	
}
