package controladores;

import java.util.List;

import javax.ejb.Local;


import dominio.Pais;
import dominio.Deportista;
import dominio.Evento;

@Local
public interface IPaisController {
		
	public List<String> altaPais(Pais pais, String username);
	
	public List<Deportista> misDeportistas(String deportista);
	public Pais buscarPais(String pais);	
	public List<Pais> listarPaises();
	public boolean borrarPais(Pais pais);	
	public void asignarEvento(Evento elevento, Pais country);	
	public List<Pais> listarPaises(Evento elevento);	
	public boolean existePais(String paisName);
	
}
