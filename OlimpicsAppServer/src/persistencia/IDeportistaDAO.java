package persistencia;

import java.util.List;

import javax.ejb.Local;

import dominio.Deportista;
import dominio.Pais;
import dominio.Usuario;

@Local
public interface IDeportistaDAO {
	
	public boolean guardarDeportista(Deportista deportista);	
	public boolean existeDeportista(Deportista deportista);
	public Deportista getDeportista(String nombreDep);
	public void modificarDeportista(Deportista dep);
		
	public List<Deportista> listarDeportistas();

	public List<Deportista> asignarDeportistas(Pais pais);
	public List<Deportista> misDeportistas(Pais miPais);
	
	public boolean asignarUsuario(Usuario u, Deportista deportista);
			
}

