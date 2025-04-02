package off.itgoes.challenge.databaseTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import off.itgoes.challenge.programmazione.Film;
import off.itgoes.challenge.programmazione.FilmRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FilmQueriesTests {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private FilmRepository filmRepository;
	
	@Test
	public void givenFilmsInProgrammazionePresenti_whenSearched_thenRetrievedOk() {
		
		// given
		creaInsiemeDiFilmPerTestRicerca();
		
		// when
		LocalDate now = LocalDate.of(2025, 01, 10);
		
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
		TestHelperFilm.createFilm(entityManager, "Il dottor Stranamore", 
				LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 14));
		
		TestHelperFilm.createFilm(entityManager, "2001: odissea nello spazio", 
				LocalDate.of(2025, 1, 8), LocalDate.of(2025, 1, 21));
		
		TestHelperFilm.createFilm(entityManager, "Barry Lyndon", 
				LocalDate.of(2025, 1, 15), LocalDate.of(2025, 1, 21));
		
		TestHelperFilm.createFilm(entityManager, "Arancia meccanica", 
				LocalDate.of(2025, 1, 21), LocalDate.of(2025, 1, 28));
	}
}
