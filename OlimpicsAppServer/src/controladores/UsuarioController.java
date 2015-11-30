package controladores;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dominio.Roles;
import dominio.Usuario;
import persistencia.IUsuarioDAO;
import persistencia.IDeportistaDAO;

@Stateless
public class UsuarioController implements IUsuarioController {

	@EJB
	private IUsuarioDAO UsuarioDAO;
	@EJB
	private IDeportistaDAO DeportistaDAO;
	
	
	/////////////////Guarda usuario comun, delegacion y organizador////////////////////////////////
	
	public boolean guardarUsuario(String nick, String Password) {
		
		try{
					
			Usuario u = new Usuario(nick,Password);
						
			if (u.getNick().equals("Admin")){
				System.out.println("Soy el admin");
				Roles r = new Roles(1,"Admin");
				u.setRoles(r);
				
			}
			else{
				System.out.println("Soy el user");
				Roles r = new Roles(2,"User");
				u.setRoles(r);
			}
									
			return UsuarioDAO.guardarUsuario(u);				
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
	
	
	///////////////////////Chequea si existe el usuario en el sistema/////////////////////
	
	public boolean existeUsuario(String nick, String password) {
		
		try{
			return UsuarioDAO.existeUsuario(new Usuario(nick,password));
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	//////////////////Dado un nick devuelve el rol del usuario//////////////7

	public String darRol(String nick){
		try{
			return UsuarioDAO.darol(nick);
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	

	////////////////////////busca un determinado usuario en la BD/////////////////////////////

	public Usuario buscarUsuario(String nick) {
		
		try{
		return UsuarioDAO.getUsuario(nick);
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return null;
	}

	
	///////////////////////////Modifica un usuario ya existente en el sistema///////////////////
	
	public void modificarUsuario(Usuario u) {
		try{
	
			UsuarioDAO.modificarUsuario(u);
		}catch(Exception e){
			e.printStackTrace();			
		}		
	}
	

	//////////////////////Guarda a la delegacion en el sistema////////////////////////
	
	public boolean guardarDelegacion(String nick, String Password) {
		
		try{							
			Usuario d = new Usuario(nick,Password);			
			System.out.println("Soy una delegacion");
			Roles r = new Roles(3,"Delegacion");
			d.setRoles(r);			
			return UsuarioDAO.guardarDelegacion(d);			
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}		
	}
	
	
	
	/////////////////////////Guarda a los organizadores en el sistema//////////////////////////
	
	public boolean guardarOrganizador(String nick, String Password) {
		
		try{							
			Usuario org = new Usuario(nick,Password);			
			System.out.println("Soy un organizador");
			Roles r = new Roles(4,"Organizador");
			org.setRoles(r);			
			return UsuarioDAO.guardarOrganizador(org);			
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}		
	}

	///////////////////////Chequea si una delegacion tiene un pais asociado///////////////////////
	
	public boolean tienePais(String nick){
		
		try{
			return UsuarioDAO.tienePais(nick);
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	///////////////////Devuelve el pais de una delegacion/////////////////////7
	
	public String darPais(String nick) {
		  
		  try{
			  
		   String nombrePais = UsuarioDAO.darPais(nick);
		   return nombrePais;
		  }
		  catch(Exception e){
		   e.printStackTrace();
		  }
		  return null;
	}

	
	/////////////////////Chequea si un usuario comun tiene un pais en sus preferencias//////////////
	
	public boolean tienePaisFav(String nick){
		
		try{
			return UsuarioDAO.tienePaisFav(nick);
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/////////////////////Chequea si un usuario comun tiene un deportista en sus preferencias//////
	
	public boolean tieneDepFav(String nick){
		
		try{
			return UsuarioDAO.tieneDepFav(nick);
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
}
