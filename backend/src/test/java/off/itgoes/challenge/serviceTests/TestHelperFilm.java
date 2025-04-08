package off.itgoes.challenge.serviceTests;

import java.time.LocalDate;

import off.itgoes.challenge.programmazione.film.Film;
import off.itgoes.challenge.programmazione.film.FilmDto;
import off.itgoes.challenge.programmazione.film.FilmService;
import off.itgoes.challenge.programmazione.sala.Sala;
import off.itgoes.challenge.programmazione.tecnologia.Tecnologia;

public class TestHelperFilm {

	public static Film createFilm(FilmService filmService, String titolo, 
			LocalDate inizioProgrammazione, LocalDate fineProgrammazione,
			Tecnologia tecnologia, Sala sala) {
		
		FilmDto filmDto = new FilmDto();
		
		filmDto.setTitolo(titolo);
		
		filmDto.setInizioProgrammazione(inizioProgrammazione);
		filmDto.setFineProgrammazione(fineProgrammazione);
		
		filmDto.setNomeTecnologia(tecnologia.getNome());
		filmDto.setIdTecnologia(tecnologia.getId());
		
		filmDto.setNomeSala(sala.getNome());
		filmDto.setIdSala(sala.getId());
		
		Film savedEntity = filmService.createFilm(filmDto);
		
		return savedEntity;
	}
}
