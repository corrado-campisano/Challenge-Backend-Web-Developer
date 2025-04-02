package off.itgoes.challenge.repositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import off.itgoes.challenge.programmazione.film.Film;
import off.itgoes.challenge.programmazione.film.FilmRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FilmRepositoryTests {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private FilmRepository filmRepository;
	
	@Test
	public void givenFilmsInProgrammazionePresenti_whenSearched_thenRetrievedOk() {
		
		// given
		creaInsiemeDiFilmPerTestRicerca();
		
		// when
		LocalDate now = LocalDate.of(2025, 01, 22);
		
		List<Film> filmsInProgrammazione = filmRepository
				.findAllByInizioProgrammazioneLessThanEqualAndFineProgrammazioneGreaterThanEqual(now, now);
		
		// then
		assertEquals(2, filmsInProgrammazione.size());
	}

	@Test
	public void givenStoricoFilmsPresente_whenSearched_thenRetrievedOk() {
		
		// given
		creaInsiemeDiFilmPerTestRicerca();
		
		// when
		LocalDate now = LocalDate.of(2025, 01, 22);
		
		List<Film> filmsInProgrammazione = filmRepository
				.findAllByFineProgrammazioneBefore(now);
		
		// then
		assertEquals(3, filmsInProgrammazione.size());
	}
	
	public void creaInsiemeDiFilmPerTestRicerca() {
		
		// su ricerca il 2025-01-22: passato
		TestHelperFilm.createFilm(entityManager, "Il dottor Stranamore", 
				LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 14));
		
		// su ricerca il 2025-01-22: passato
		TestHelperFilm.createFilm(entityManager, "2001: odissea nello spazio", 
				LocalDate.of(2025, 1, 8), LocalDate.of(2025, 1, 21));
		
		// su ricerca il 2025-01-22: passato
		TestHelperFilm.createFilm(entityManager, "Barry Lyndon", 
				LocalDate.of(2025, 1, 15), LocalDate.of(2025, 1, 21));
		
		// su ricerca il 2025-01-22: in programmazione
		TestHelperFilm.createFilm(entityManager, "Arancia meccanica", 
				LocalDate.of(2025, 1, 8), LocalDate.of(2025, 1, 28));
		
		// su ricerca il 2025-01-22: in programmazione
		TestHelperFilm.createFilm(entityManager, "Full Metal Jacket", 
				LocalDate.of(2025, 1, 15), LocalDate.of(2025, 1, 28));
	}
}
