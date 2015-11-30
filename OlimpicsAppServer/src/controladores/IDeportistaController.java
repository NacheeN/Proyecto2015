package controladores;

import java.util.List;

import javax.ejb.Local;

import dominio.Deportista;
@Local
public interface IDeportistaController {
	
	public boolean guardarDeportista(String nombreDep, int edad, float altura, float peso, String cuentaT);
	public boolean existeDeportista(String nombreDep, int edad, float altura, float peso,  String cuentaT);	
	public Deportista buscarDeportista(String nombreDep);	
	public void modificarDeportista(Deportista dep);
	
	public List<String> altaDeportista(Deportista deportista, String username);
	
	public List<Deportista> listarDeportistas();

}
