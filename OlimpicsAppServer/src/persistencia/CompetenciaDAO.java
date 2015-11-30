package persistencia;

import javax.ejb.Stateless;

import dominio.Competencia;
import dominio.Usuario;

@Stateless
public class CompetenciaDAO implements ICompetenciaDAO {

	
	@javax.persistence.PersistenceContext(unitName = "OlimpicsAppServer")
	private javax.persistence.EntityManager em;
	
	public boolean guardarCompetencia(Competencia competencia) {
		
		try {
			em.persist(competencia);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	public void modificarCompetencia(Competencia comp){
		
		try {
			em.merge(comp);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	
	public boolean existeCompetencia(Competencia comp) {
		
		Competencia compResultado = em.find(Competencia.class, comp.getId());
		
		if ((compResultado != null) && (compResultado.getNombre().equals(comp.getNombre())))
		return true;
		
		else return false;
	}
	
	public Competencia getCompetencia (String nombre){
		return em.find(Competencia.class, nombre);
	}
	
	
	
	@Override
	public boolean asignarUsuario(Usuario u,Competencia c) {
		
		try {			
			c.setCreador(u);		
			em.merge(c);
			return true;			
		}
		catch(Exception ex){
			ex.printStackTrace();			
		}
		return false;	
	}

}
