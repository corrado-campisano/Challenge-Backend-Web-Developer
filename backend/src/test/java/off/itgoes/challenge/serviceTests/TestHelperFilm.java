package off.itgoes.challenge.serviceTests;

import java.time.LocalDate;

import off.itgoes.challenge.programmazione.film.Film;
import off.itgoes.challenge.programmazione.film.FilmDto;
import off.itgoes.challenge.programmazione.film.FilmService;

public class TestHelperFilm {

	public static Film createFilm(FilmService filmService, String titolo, 
			LocalDate inizioProgrammazione, LocalDate fineProgrammazione, String sala) {
		
		FilmDto filmDto = new FilmDto();
		
		filmDto.setTitolo(titolo);
		filmDto.setInizioProgrammazione(inizioProgrammazione);
		filmDto.setFineProgrammazione(fineProgrammazione);
		filmDto.setSalaDiProiezione(sala);
		
		Film savedEntity = filmService.validateAndCreateFilm(filmDto);
		
		return savedEntity;
	}
}
