package persistencia;

import java.util.List;
import javax.ejb.Local;
import dominio.Evento;
import dominio.Noticia;
import dominio.Usuario;

@Local
public interface IEventoDAO {

		
		public boolean guardarNoticia(Noticia noticia);		
		
		public boolean guardarEvento(Evento evento);	
		public Evento buscarEvento(String evento);
		public List<Evento> listarEventos();
		public boolean borrarEvento(Evento evento);
		public boolean asignarUsuario(Usuario u, Evento evento);
		public boolean existeEvento(String nombreE);
		
}