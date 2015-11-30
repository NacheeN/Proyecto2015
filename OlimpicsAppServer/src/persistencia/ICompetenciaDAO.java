package persistencia;

import javax.ejb.Local;

import dominio.Competencia;
import dominio.Usuario;

@Local
public interface ICompetenciaDAO {
	
	public boolean guardarCompetencia(Competencia competencia);
	public void modificarCompetencia(Competencia competencia);
	public boolean existeCompetencia(Competencia competencia);
	public Competencia getCompetencia(String nombre);
	public boolean asignarUsuario(Usuario u, Competencia competencia);

}
