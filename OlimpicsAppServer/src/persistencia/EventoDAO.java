package persistencia;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import dominio.Noticia;
import dominio.Evento;
import dominio.Usuario;


@Stateless
public class EventoDAO implements IEventoDAO {
	
	@javax.persistence.PersistenceContext(unitName="OlimpicsAppServer")
	private javax.persistence.EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean guardarEvento(Evento evento){
				
		try {
			em.persist(evento);
			return true;			
		} 
		catch (Exception e) {		
			e.printStackTrace();			
		}		
		return false;		
	}
	
	
	public List<Evento> listarEventos() {
		
		try {		
			List<Evento> eventos = em.createQuery("Select e FROM Eventos e",Evento.class).getResultList();		
			return eventos;	
		}	
		catch (Exception e) {
			e.printStackTrace();
		}		
		return null;
	}


	public Evento buscarEvento(String evento) {
		
		Evento e = em.find(Evento.class, evento); 
		return e;	
	}
	
	
	public boolean borrarEvento(Evento evento){

		try {			
			Evento eventoBorrar = em.getReference(Evento.class, evento.getNombreE());
			em.remove(eventoBorrar);			
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	@Override
	public boolean asignarUsuario(Usuario u,Evento e) {
		
		try {			
			e.setOrganizador(u);			
			em.merge(e);
			return true;			
		}
		catch(Exception ex){
			ex.printStackTrace();			
		}
		return false;	
	}


	
	

	


	public boolean existeEvento(String nombreE) {
	
		boolean existe = false;
		try {
			
			Evento e = em.find(Evento.class, nombreE);	
			if(e!=null){
				existe=true;
			}
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return existe;
	}

	
	
	public boolean guardarNoticia(Noticia noticia) {

		try {
			em.persist(noticia);
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
}
	
	
	
	
	
	
	
	
	

