package persistencia;



import javax.ejb.Local;

import dominio.Competencia;
import dominio.Deportista;
import dominio.Evento;
import dominio.Noticia;
import dominio.Pais;
import dominio.Usuario;

@Local
public interface IUsuarioDAO {

	public boolean guardarUsuario(Usuario usuario);	
	public boolean existeUsuario(Usuario usuario);
	public Usuario getUsuario(String nick);
	public String darol(String nick);
	public void modificarUsuario(Usuario u);
	
	public boolean guardarDelegacion(Usuario delegacion);	
	public boolean guardarOrganizador(Usuario organizador);	
	
	
	public boolean asignarEvento(Usuario u, Evento evento);
	public boolean asignarCompetencia(Usuario u, Competencia competencia);
	public boolean asignarNoticia(Usuario u, Noticia noticia);
	
	public boolean tienePaisFav(String nick);
	public boolean tieneDepFav(String nick);

	public boolean tienePais(String nick);
	public boolean asignarPais(Usuario u, Pais pais);
	public String darPais(String nick) ;
	public boolean asignarDeportista(Usuario u, Deportista deportista);

	
}



 