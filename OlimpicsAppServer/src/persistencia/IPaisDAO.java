package persistencia;

import java.util.List;

import javax.ejb.Local;

import dominio.Pais;
import dominio.Evento;
import dominio.Usuario;


@Local
public interface IPaisDAO {
	
	public boolean guardarPais(Pais pais);	
	public Pais buscarPais(String pais);
	public List<Pais> listarPaises();
	public boolean borrarPais(Pais pais);
	public boolean asignarUsuario(Usuario u, Pais pais);
	public boolean existePais(String paisName);
	boolean asignarEvento(Evento e, Pais p);
	public List<Pais> listarPaises(Evento elevento);
	
	
}
