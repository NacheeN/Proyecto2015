package controladores;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import persistencia.ICompetenciaDAO;
import persistencia.IUsuarioDAO;
import persistencia.UsuarioDAO;
import dominio.Competencia;
import dominio.Noticia;
import dominio.Usuario;


@Stateless
public class CompetenciaController implements ICompetenciaController{

	@EJB
	private ICompetenciaDAO CompetenciaDAO;
	@EJB
	private IUsuarioDAO UsuarioDAO;
	
	
	public boolean guardarCompetencia(String nombre){
		
		try {
			Competencia compe = new Competencia(nombre);
			return CompetenciaDAO.guardarCompetencia(compe);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean existeCompetencia(String nombre) {
		
		try{
			return CompetenciaDAO.existeCompetencia(new Competencia(nombre));
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	public Competencia buscarCompetencia(String nombre) {
		
		try{
		return CompetenciaDAO.getCompetencia(nombre);
		}catch(Exception e){
			e.printStackTrace();			
		}
		return null;
	}


	public void modificarCompetencia(Competencia comp) {
		try{
	
			CompetenciaDAO.modificarCompetencia(comp);
		}catch(Exception e){
			e.printStackTrace();			
		}		
	}

	
	public ArrayList<String> altaCompetencia(Competencia competencia,String username){		
		
		try {			
			
				if(CompetenciaDAO.guardarCompetencia(competencia)){
				Usuario u = UsuarioDAO.getUsuario(username);
				CompetenciaDAO.asignarUsuario(u,competencia);				
				competencia.setEvento(u.getEvento());;
				UsuarioDAO.asignarCompetencia(u,competencia);					
				}
		} 
		catch (Exception e) {		
			e.printStackTrace();			
		}
		
		return null;
	}
	
}
