package controladores;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import persistencia.IPaisDAO;
import persistencia.IDeportistaDAO;
import persistencia.IEventoDAO;
import persistencia.IUsuarioDAO;
import dominio.Pais;
import dominio.Deportista;
import dominio.Evento;
import dominio.Usuario;



@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PaisController implements IPaisController {
	
	@EJB
	private IPaisDAO paisDAO;
	@EJB
	private IUsuarioDAO usuarioDAO;	
	@EJB
	private IDeportistaDAO deportistaDAO;	
	@EJB
	private IEventoDAO eventoDAO;
	
	
	
	///////////Da de alta el pais y le setea la delegacion/////////////////////////////////
	
	public ArrayList<String> altaPais(Pais pais,String username){
		
		ArrayList<String> altaPais = new ArrayList<String>();
		try {               
				
				if(paisDAO.guardarPais(pais)){
				
					Usuario u = usuarioDAO.getUsuario(username);
					paisDAO.asignarUsuario(u,pais);
					usuarioDAO.asignarPais(u,pais);				
					return altaPais;
				}
		} 
		catch (Exception e) {		
			e.printStackTrace();			
		}		
		return null;
	}

	
	/////////////////////Lista los deportistas del pais ////////////////////
	
	public List<Deportista> misDeportistas(String pais){
		
		List<Deportista> deportistas = new ArrayList<Deportista>();
		try{
			Pais miPais = paisDAO.buscarPais(pais); 			
			deportistas = deportistaDAO.misDeportistas(miPais);			
			return deportistas;
		}
		catch(Exception e){
			e.printStackTrace();		
		}		
		return null;	
	}

	
/////////////////////////////Lista todos los paises existentes/////////////////////////////	
	
	public List<Pais> listarPaises() {
		
		List<Pais> paises = paisDAO.listarPaises();
		return paises;		  
	}

	
	/////////////////////////Busca el pais en la BD//////////////////////////////////////////////
	
	public Pais buscarPais(String pais) {		
		
		try{				
			Pais p = paisDAO.buscarPais(pais);
			return p;
			}
			catch(Exception ex){
				ex.printStackTrace();			
			}			
		return null;	
	}
	
	
	/////////////////////////////Borra el pais de la BD//////////////////////////////////////
	
	public boolean borrarPais(Pais pais){

		try {			
		    paisDAO.borrarPais(pais);
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	///////////////////////////Le asigna un evento al pais/////////////////////////////////////
	
	public void asignarEvento(Evento evento, Pais country){
		
		try {			
		    paisDAO.asignarEvento(evento,country);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	////////////////////////Lista todos los paises de un determinado evento///////////////////////
	@Override
	public List<Pais> listarPaises(Evento elevento) {
		
		List<Pais> paises = paisDAO.listarPaises(elevento);
		return paises;		
	}
	
	
	
	////////////////////////Chequea que dado el nombre de un pais el mismo exista en la BD/////////
	
	public boolean existePais(String paisName) {
		
		boolean existe = false ;
		try{
			existe = paisDAO.existePais(paisName); 			
		}
		catch(Exception e){
			e.printStackTrace();			
		}		
		return existe;
	}
	
	
	

}
