package persistencia;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import dominio.Pais;
import dominio.Evento;
import dominio.Usuario;


@Stateless
public class PaisDAO implements IPaisDAO {
	
	@javax.persistence.PersistenceContext(unitName="OlimpicsAppServer")
	private javax.persistence.EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean guardarPais(Pais pais){
				
		try {
			em.persist(pais);
			return true;			
		} 
		catch (Exception e) {		
			e.printStackTrace();			
		}		
		return false;		
	}
	
	
	public List<Pais> listarPaises() {
		
		List<Pais> paises = em.createQuery("Select p FROM Pais p",Pais.class).getResultList();		
		return paises;		  
	}


	public Pais buscarPais(String pais) {
		
		Pais p = em.find(Pais.class, pais); 
		return p;	
	}
	
	
	public boolean borrarPais(Pais pais){

		try {			
			Pais paisBorrar = em.getReference(Pais.class, pais.getNombrePais());
			em.remove(paisBorrar);			
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	@Override
	public boolean asignarUsuario(Usuario u,Pais p) {
		
		try {			
			p.setDelegacion(u);			
			em.merge(p);
			return true;			
		}
		catch(Exception ex){
			ex.printStackTrace();			
		}
		return false;	
	}


	
	public boolean asignarEvento(Evento e, Pais p) {
		try {
			
			p.setEvento(e);			
			em.merge(p);			
			return true;
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Pais> listarPaises(Evento elevento) {

		try {
			String nombreEvento = elevento.getNombreE();
			List<Pais> paises = em.createQuery("Select p FROM Pais p WHERE p.evento = '"+nombreEvento+"'",Pais.class).getResultList();
			return paises;
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
			
		return null;
	}


	public boolean existePais(String paisName) {
	
		boolean existe = false;
		try {
			
			Pais p = em.find(Pais.class, paisName);	
			if(p!=null){
				existe=true;
			}
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return existe;
	}

}
