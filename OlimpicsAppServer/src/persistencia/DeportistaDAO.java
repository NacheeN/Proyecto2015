package persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dominio.Deportista;
import dominio.Pais;
import dominio.Usuario;

@Stateless
public class DeportistaDAO implements IDeportistaDAO {

	@javax.persistence.PersistenceContext(unitName = "OlimpicsAppServer")
	private javax.persistence.EntityManager em;

	@EJB
	private IPaisDAO paisDAO;
	
	public boolean guardarDeportista(Deportista deportista) {

		try {
			em.persist(deportista);
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public void modificarDeportista(Deportista dep) {		
		
		try {
			em.merge(dep);	
		} 
		catch (Exception e){
			e.printStackTrace();
		}		
	}


	public boolean existeDeportista(Deportista deportista) {

		Deportista dep = em.find(Deportista.class, deportista.getIdDep()); 

		if ((dep != null) && (dep.getNombreDep().equals(deportista.getNombreDep())))
			return true;
		else
			return false;
	}

	
	public Deportista getDeportista(String nombreDep) {		
		
		return em.find(Deportista.class, nombreDep);
	}	
	
	
	public List<Deportista> listarDeportistas() {
		
		try {
			List<Deportista> deportistas = em.createQuery("Select * From Deportista d", Deportista.class).getResultList();
			return deportistas;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	
	@SuppressWarnings("unchecked")
	public List<Deportista> asignarDeportistas(Pais pais) {
		
		List<Deportista> deportistasEnPais = new ArrayList<Deportista>();

		
		List<Deportista> deportistas = em.createQuery("SELECT d FROM Deportista d WHERE d.pais IS NULL").setMaxResults(23).getResultList();
		
		for (Deportista dep : deportistas) {

			dep.setPais(pais);
			dep.cargarJugador(dep.getNombreDep());			
			deportistasEnPais.add(dep);
		}
		return deportistasEnPais;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Deportista> misDeportistas(Pais miPais){
		
		try{
			
			List<Deportista> deportistas = em.createQuery("SELECT d FROM Deportista d WHERE d.pais LIKE :pais").setParameter("equipo", miPais).getResultList();
			return deportistas;			
		}
		catch(Exception e){
			e.printStackTrace();
		}		
		return null;		
	}
	
	
	
	
	@Override
	public boolean asignarUsuario(Usuario u,Deportista d) {
		try {			
			d.setDelegacion(u);			
			em.merge(d);
			return true;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	

	
}