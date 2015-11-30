package persistencia;

import javax.ejb.Local;
import dominio.Noticia;
import dominio.Usuario;


@Local
public interface INoticiaDAO {
	
	public boolean guardarNoticia(Noticia noticia);		
	public boolean asignarUsuario(Usuario u, Noticia noticia);
	
}