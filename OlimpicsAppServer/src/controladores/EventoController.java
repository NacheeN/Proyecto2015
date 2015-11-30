package controladores;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;






import persistencia.IDeportistaDAO;
import persistencia.IEventoDAO;
import persistencia.INoticiaDAO;
import persistencia.IUsuarioDAO;
import dominio.Deportista;
import dominio.Noticia;
import dominio.Evento;
import dominio.Usuario;



@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EventoController implements IEventoController {
	
	
	@EJB
	private IUsuarioDAO UsuarioDAO;	
	@EJB
	private IDeportistaDAO deportistaDAO;	
	@EJB
	private IEventoDAO EventoDAO;
	@EJB
	private INoticiaDAO NoticiaDAO;
	
	
	
	///////////Da de alta el pais y le setea la delegacion/////////////////////////////////
	
	public ArrayList<String> altaEvento(Evento evento,String username){
		
		ArrayList<String> altaEvento = new ArrayList<String>();
		try {               
				
				if(EventoDAO.guardarEvento(evento)){
				
					Usuario u = UsuarioDAO.getUsuario(username);
					EventoDAO.asignarUsuario(u,evento);
					UsuarioDAO.asignarEvento(u,evento);				
					return altaEvento;
				}
		} 
		catch (Exception e) {		
			e.printStackTrace();			
		}		
		return null;
	}

	
	
	
/////////////////////////////Lista todos los paises existentes/////////////////////////////	
	
	public List<Evento> listarEventos() {
		
		List<Evento> eventos = EventoDAO.listarEventos();
		return eventos;		  
	}


	/////////////////////////Busca el pais en la BD//////////////////////////////////////////////
	
	public Evento buscarEvento(String evento) {		
		
		try{				
			Evento e = EventoDAO.buscarEvento(evento);
			return e;
			}
			catch(Exception ex){
				ex.printStackTrace();			
			}			
		return null;	
	}
	
	
	/////////////////////////////Borra el pais de la BD//////////////////////////////////////
	
	public boolean borrarEvento(Evento evento){

		try {			
		    EventoDAO.borrarEvento(evento);
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	

	
	////////////////////////Chequea que dado el nombre de un pais el mismo exista en la BD/////////
	
	public boolean existeEvento(String nombreE) {
		
		boolean existe = false ;
		try{
			existe = EventoDAO.existeEvento(nombreE); 			
		}
		catch(Exception e){
			e.printStackTrace();			
		}		
		return existe;
	}
	

	/////////////////////////////////Guarda una noticia///////////////////////////////////
	
	public boolean guardarNoticia(String titulo, String desc, String tag) {
		
		try{							
			Noticia not = new Noticia(titulo,desc, tag);	
			
			return EventoDAO.guardarNoticia(not);		
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}		
	}
	
	
	public ArrayList<String> altaNoticia(Noticia noticia,String username){
		
		
		try {			
			
				if(NoticiaDAO.guardarNoticia(noticia)){
				Usuario u = UsuarioDAO.getUsuario(username);
				NoticiaDAO.asignarUsuario(u,noticia);				
				noticia.setEvento(u.getEvento());;
				UsuarioDAO.asignarNoticia(u,noticia);					
				}
		} 
		catch (Exception e) {		
			e.printStackTrace();			
		}
		
		return null;
	}
	
}