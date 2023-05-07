package bookle.test;

import java.time.LocalDate;

import bookle.modelo.Actividad;
import bookle.modelo.Agenda;
import bookle.modelo.Turno;
import repositorio.RepositorioException;
import repositorio.RepositorioMemoria;

/*
 * En la carpeta de código fuente se crea el fichero "repositorios.properties" para 
 * reemplazar el valor por defecto (configurado en el proyecto Bookle) y establecer el repositorio de pruebas
 */

public class RepositorioTest extends RepositorioMemoria<Actividad> {

	public RepositorioTest() {
		
		// crea unas actividades de prueba
		
		 // Actividad 1
        
   		Actividad actividad1 = new Actividad();
		actividad1.setTitulo("Entrevistas de prácticas");
		actividad1.setDescripcion("Enlace Zoom: ...");
		actividad1.setProfesor("Marcos");
		actividad1.setEmail("marcos@um.es");

		// Día 1

		Agenda dia1 = new Agenda();		
		dia1.setFecha(LocalDate.now().plusDays(1)); // mañana

		for (int hora = 10; hora <= 14; hora++) {

			Turno turno = new Turno();
			turno.setHorario(hora + ":00h");
			dia1.getTurno().add(turno);
		}

		actividad1.getAgenda().add(dia1);
		
		try {
			add(actividad1);
		} catch (RepositorioException e) {
			// Es un repositorio en memoria, no debe suceder
			e.printStackTrace();
		}
		
		// Actividad 2
        
   		Actividad actividad2 = new Actividad();
		actividad2.setTitulo("Tutorias");
		actividad2.setDescripcion("Enlace Zoom: ...");
		actividad2.setProfesor("Juan");
		actividad2.setEmail("juan@um.es");

		// Día 1

		dia1 = new Agenda();		
		dia1.setFecha(LocalDate.now().plusDays(1)); // mañana

		for (int hora = 10; hora <= 14; hora++) {

			Turno turno = new Turno();
			turno.setHorario(hora + ":00h");
			dia1.getTurno().add(turno);
		}

		actividad2.getAgenda().add(dia1);
		
		try {
			add(actividad2);
		} catch (RepositorioException e) {
			// Es un repositorio en memoria, no debe suceder
			e.printStackTrace();
		}
		
	}
	
}
