package persistencia;


import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import dominio.Noticia;
import dominio.Usuario;


@Stateless
public class NoticiaDAO implements INoticiaDAO {
	
	@javax.persistence.PersistenceContext(unitName="OlimpicsAppServer")
	private javax.persistence.EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean guardarNoticia(Noticia noticia){
				
		try {
			em.persist(noticia);
			return true;			
		} 
		catch (Exception e) {		
			e.printStackTrace();			
		}		
		return false;		
	}
	

	@Override
	public boolean asignarUsuario(Usuario u,Noticia n) {
		
		try {			
			n.setPublicador(u);			
			em.merge(n);
			return true;			
		}
		catch(Exception ex){
			ex.printStackTrace();			
		}
		return false;	
	}


}
