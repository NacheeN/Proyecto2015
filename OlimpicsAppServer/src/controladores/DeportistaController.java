package controladores;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dominio.Deportista;
import dominio.Pais;
import dominio.Usuario;
import persistencia.IDeportistaDAO;
import persistencia.IUsuarioDAO;

@Stateless
public class DeportistaController implements IDeportistaController {

	@EJB
	private IDeportistaDAO DeportistaDAO;
	@EJB
	private IUsuarioDAO UsuarioDAO;
	
	
	public boolean guardarDeportista(String nombreDep, int edad, float altura, float peso, String cuentaT){
		
		try{
							
			Deportista dep = new Deportista(nombreDep, edad, altura, peso,  cuentaT);
			return DeportistaDAO.guardarDeportista(dep);			
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}		
	}
	
	
	
	public boolean existeDeportista(String nombreDep, int edad, float altura, float peso, String cuentaT) {
		
		try{
			return DeportistaDAO.existeDeportista(new Deportista( nombreDep,  edad,  altura,  peso, cuentaT));
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	public Deportista buscarDeportista(String nombreDep) {
		
		try{
		return DeportistaDAO.getDeportista(nombreDep);
		}catch(Exception e){
			e.printStackTrace();			
		}
		return null;
	}


	public void modificarDeportista(Deportista dep) {
		try{
	
			DeportistaDAO.modificarDeportista(dep);
		}catch(Exception e){
			e.printStackTrace();			
		}		
	}

	
	public List<Deportista> listarDeportistas() {
		
		List<Deportista> deportistas = DeportistaDAO.listarDeportistas();
		return deportistas;		  
	}
	
	
	
	public ArrayList<String> altaDeportista(Deportista deportista,String username){
		
		
		try {			
			
				if(DeportistaDAO.guardarDeportista(deportista)){
				Usuario u = UsuarioDAO.getUsuario(username);
				DeportistaDAO.asignarUsuario(u,deportista);				
				deportista.setPais(u.getPais());
				UsuarioDAO.asignarDeportista(u,deportista);		
					
				}
		} 
		catch (Exception e) {		
			e.printStackTrace();			
		}
		
		return null;
	}
		
			
			
}