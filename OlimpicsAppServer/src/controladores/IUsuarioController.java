package controladores;



import javax.ejb.Local;
import dominio.Usuario;

@Local
public interface IUsuarioController {
	public boolean guardarUsuario(String nick, String Password);
	public boolean existeUsuario(String nick, String password);	
	public String darRol(String nick);	
	public Usuario buscarUsuario(String nick);	
	public void modificarUsuario(Usuario u);
	
	public boolean guardarDelegacion(String nick, String password);
	public boolean guardarOrganizador(String nick, String password);

	
	public boolean tienePais(String nick);
	public String darPais(String nick);
	public boolean tienePaisFav(String nick);
	public boolean tieneDepFav(String nick);
	
	
}
