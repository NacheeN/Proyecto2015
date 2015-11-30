package controladores;

import java.util.List;
import javax.ejb.Local;
import dominio.Evento;
import dominio.Noticia;


@Local
public interface IEventoController {
	
	public List<String> altaEvento(Evento evento, String username);	
	public Evento buscarEvento(String evento);	
	public List<Evento> listarEventos();
	public boolean borrarEvento(Evento evento);		
	public boolean existeEvento(String eventoNombre);
	
	public boolean guardarNoticia(String titulo, String desc, String tag);
	
	public List<String> altaNoticia(Noticia noticia, String username);
	
	
	
}
