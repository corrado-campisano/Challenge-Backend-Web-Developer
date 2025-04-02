package off.itgoes.challenge.serviceTests;

import java.time.LocalDate;

import off.itgoes.challenge.programmazione.Film;
import off.itgoes.challenge.programmazione.FilmDto;
import off.itgoes.challenge.programmazione.FilmService;

public class TestHelperFilm {

	public static Film createFilm(FilmService filmService, String titolo, LocalDate inizioProgrammazione, LocalDate fineProgrammazione) {
		
		FilmDto filmDto = new FilmDto();
		
		filmDto.setTitolo(titolo);
		filmDto.setInizioProgrammazione(inizioProgrammazione);
		filmDto.setFineProgrammazione(fineProgrammazione);
		
		Film savedEntity = filmService.validateAndCreateFilm(filmDto);
		
		return savedEntity;
	}
}
