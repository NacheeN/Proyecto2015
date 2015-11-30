package persistencia;

import javax.ejb.Stateless;

import dominio.Competencia;
import dominio.Deportista;
import dominio.Evento;
import dominio.Noticia;
import dominio.Pais;
import dominio.Usuario;


@Stateless
public class UsuarioDAO implements IUsuarioDAO {

	@javax.persistence.PersistenceContext(unitName = "OlimpicsAppServer")
	private javax.persistence.EntityManager em;

	public boolean guardarUsuario(Usuario usuario) {

		try {
			em.persist(usuario);
			return true;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return false;

	}
	
	
	public void modificarUsuario(Usuario u) {
		
		try {
			em.merge(u);			

		} catch (Exception e) {

			e.printStackTrace();

		}
		
	}


	public boolean existeUsuario(Usuario usuario) {

		Usuario u = em.find(Usuario.class, usuario.getNick()); // Si no se encuentra a la persona, se retorna NULL

		if ((u != null) && (u.getPassword().equals(usuario.getPassword())))
			return true;
		else
			return false;
	}

	
	public Usuario getUsuario(String nick) {
		
		return em.find(Usuario.class, nick);
	}


	
	public String darol(String nick) {
		
		Usuario u = em.find(Usuario.class, nick);
		if (u.getRoles().getId() == 1) {
			return "Admin";
		} 
		else if (u.getRoles().getId() == 2) {
			return "Usuario";
		}
		else if (u.getRoles().getId() == 3){
			return "Delegacion";
		}
		else {
			return "Organizador";
		}

	}
	
	
	
	
	public boolean guardarDelegacion(Usuario delegacion) {

		try {
			em.persist(delegacion);
			return true;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return false;

	}
	
	
	public boolean guardarOrganizador(Usuario organizador) {

		try {
			em.persist(organizador);
			return true;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return false;

	}
	
	
	
	
	
	


public boolean asignarEvento(Usuario u, Evento evento) {
	
	try {
		u.setEvento(evento);
		em.merge(u);
		return true;
		
	} catch (Exception ex) {
		ex.printStackTrace();
	}
	return false;

}



	public boolean tienePais(String nick) {
		
		Usuario u = em.find(Usuario.class, nick);
		if (u.getPais() == null) {
			return false;
		} else
			return true;
	}



	public boolean asignarPais(Usuario u, Pais pais) {
		
		try {
			u.setPais(pais);
			em.merge(u);
			return true;
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;	
	}
	
	
	public String darPais(String nick) {
		
		  Usuario u = em.find(Usuario.class, nick);		
		  return u.getPais().getNombrePais();	
	  }


	public boolean tienePaisFav(String nick) {
		
		Usuario u = em.find(Usuario.class, nick);
		if (u.getPaisFavorito() == null) {
			return false;
		} else
			return true;
	}

	
	public boolean tieneDepFav(String nick) {
		
		Usuario u = em.find(Usuario.class, nick);
		if (u.getDeportistaFav() == null) {
			return false;
		} else
			return true;
	}

	
	public boolean asignarDeportista(Usuario u, Deportista deportista) {
		
		try {
			u.setDeportista(deportista);
			em.merge(u);
			return true;
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;	
	}
	
	
	
	public boolean asignarNoticia(Usuario u, Noticia noticia) {
		
		try {
			u.setNoticia(noticia);
			em.merge(u);
			return true;
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;	
	}
	
	
	public boolean asignarCompetencia(Usuario u, Competencia competencia) {
		
		try {
			u.setCompetencia(competencia);
			em.merge(u);
			return true;
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;	
	}
	
}
