package off.itgoes.challenge.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import off.itgoes.challenge.programmazione.film.Film;
import off.itgoes.challenge.programmazione.film.FilmDto;
import off.itgoes.challenge.programmazione.film.FilmPeriodException;
import off.itgoes.challenge.programmazione.film.FilmService;

@SpringBootTest
@Transactional
public class FilmServiceTests {

	@Autowired
	private FilmService filmService;

	@Test
	public void givenPeriodoProgrammazioneMenoDiUnaSettimana_whenCreated_thenThrowsException() {

		// given
		FilmDto filmDto = new FilmDto();

		String titolo = "titolo";
		LocalDate inizioProgrammazione = LocalDate.of(2025, 01, 10);
		LocalDate fineProgrammazione = LocalDate.of(2025, 01, 15);

		filmDto.setTitolo(titolo);
		filmDto.setInizioProgrammazione(inizioProgrammazione);
		filmDto.setFineProgrammazione(fineProgrammazione);

		// when + then
		assertThrows(FilmPeriodException.class, () -> filmService.validateAndCreateFilm(filmDto));
	}

	@Test
	public void givenPeriodoProgrammazionePiuDiTreSettimane_whenCreated_thenThrowsException() {

		// given
		FilmDto filmDto = new FilmDto();

		String titolo = "titolo";
		LocalDate inizioProgrammazione = LocalDate.of(2025, 01, 1);
		LocalDate fineProgrammazione = LocalDate.of(2025, 01, 22);

		filmDto.setTitolo(titolo);
		filmDto.setInizioProgrammazione(inizioProgrammazione);
		filmDto.setFineProgrammazione(fineProgrammazione);

		// when + then
		assertThrows(FilmPeriodException.class, () -> filmService.validateAndCreateFilm(filmDto));
	}

	@Test
	public void givenPeriodoProgrammazioneCorretto_whenCreated_thenSavedOk() {

		// given
		FilmDto filmDto = new FilmDto();

		String titolo = "titolo";
		LocalDate inizioProgrammazione = LocalDate.of(2025, 01, 1);
		LocalDate fineProgrammazione = LocalDate.of(2025, 01, 14);

		filmDto.setTitolo(titolo);
		filmDto.setInizioProgrammazione(inizioProgrammazione);
		filmDto.setFineProgrammazione(fineProgrammazione);

		// when
		Film entity = filmService.validateAndCreateFilm(filmDto);

		// then
		assertTrue(entity.getId() > 0);
	}

	@Test
	public void givenFilmsInProgrammazionePresenti_whenSearched_thenRetrievedOk() {

		// given
		creaInsiemeDiFilmPerTestRicerca();

		// when
		List<FilmDto> filmsInProgrammazione = filmService.getFilmsInProgrammazione();

		// then
		assertEquals(2, filmsInProgrammazione.size());
	}

	@Test
	public void givenStoricoFilmsPresente_whenSearched_thenRetrievedOk() {

		// given
		creaInsiemeDiFilmPerTestRicerca();

		// when
		List<FilmDto> filmsInProgrammazione = filmService.getStoricoFilmsPassati();

		// then
		assertEquals(3, filmsInProgrammazione.size());
	}

	public void creaInsiemeDiFilmPerTestRicerca() {
		// qui utilizzo date "dinamiche", come richiesto dal service,
		// ma con i valori replicati da quelle "statiche" del test sul repository:
		// vedere metodo analogo nella classe "FilmRepositoryTests"
		// del package "off.itgoes.challenge.repositoryTests" 
		
		// su ricerca il 2025-01-22: passato
		TestHelperFilm.createFilm(filmService, "Il dottor Stranamore", 
				LocalDate.now().minusDays(21),	// era 1	= 22 - 21
				LocalDate.now().minusDays(8)	// era 14	= 22 - 8
				);

		// su ricerca il 2025-01-22: passato
		TestHelperFilm.createFilm(filmService, "2001: odissea nello spazio", 
				LocalDate.now().minusDays(14),	// era 8	= 22 - 14
				LocalDate.now().minusDays(1)	// era 21	= 22 - 1
				);

		// su ricerca il 2025-01-22: passato
		TestHelperFilm.createFilm(filmService, "Barry Lyndon", 
				LocalDate.now().minusDays(7),	// era 15	= 22 - 7
				LocalDate.now().minusDays(1)	// era 21	= 22 - 1
				);

		// su ricerca il 2025-01-22: in programmazione
		TestHelperFilm.createFilm(filmService, "Arancia meccanica", 
				LocalDate.now().minusDays(14),	// era 8	= 22 - 14
				LocalDate.now().plusDays(6)		// era 28	= 22 + 6
				);
		
		// su ricerca il 2025-01-22: in programmazione
		TestHelperFilm.createFilm(filmService, "Full Metal Jacket", 
				LocalDate.now().minusDays(7),	// era 15	= 22 - 7
				LocalDate.now().plusDays(6)		// era 28	= 22 + 6
				);
	}
}
