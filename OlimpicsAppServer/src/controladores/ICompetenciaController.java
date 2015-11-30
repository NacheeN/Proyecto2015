package controladores;

import java.util.List;
import javax.ejb.Local;
import dominio.Competencia;

@Local
public interface ICompetenciaController {

	public boolean guardarCompetencia(String nombre);
	public boolean existeCompetencia(String nombre);	
	public Competencia buscarCompetencia(String nombre);	
	public void modificarCompetencia(Competencia competencia);
	
	public List<String> altaCompetencia(Competencia competencia, String username);
}
