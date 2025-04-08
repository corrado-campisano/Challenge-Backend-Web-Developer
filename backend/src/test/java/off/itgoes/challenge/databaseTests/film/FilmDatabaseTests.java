package off.itgoes.challenge.databaseTests.film;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import jakarta.validation.ConstraintViolationException;
import off.itgoes.challenge.ConstantsTestHelper;
import off.itgoes.challenge.databaseTests.sala.TestHelperSala;
import off.itgoes.challenge.databaseTests.tecnologia.TestHelperTecnologia;
import off.itgoes.challenge.programmazione.film.Film;
import off.itgoes.challenge.programmazione.film.FilmRepository;
import off.itgoes.challenge.programmazione.sala.Sala;
import off.itgoes.challenge.programmazione.tecnologia.Tecnologia;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FilmDatabaseTests {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private FilmRepository filmRepository;
	
	private Tecnologia tecnologia = null;
	private Sala sala = null;
	
	@BeforeEach
	public void creaTecnologiaAndSala() {
		tecnologia = TestHelperTecnologia.creaTecnologia(entityManager, "IMAX");
		sala = TestHelperSala.creaSala(entityManager, "sala 1", tecnologia, ConstantsTestHelper.NUMERO_MINIMO_POSTI);
	}
	
	@Test
	public void givenNullName_whenCreated_thenThrowsException() {
		
		// given
		String nome = null;
		LocalDate dataInizio = LocalDate.of(2025, 1, 1);
		LocalDate dataFine = LocalDate.of(2025, 1, 14);
		
		// when then
		assertThrows(ConstraintViolationException.class, () -> TestHelperFilm.createFilm(entityManager, nome, 
				dataInizio, dataFine, 
				tecnologia, sala));
	}
	
	@Test
	public void givenEmptyName_whenCreated_thenThrowsException() {
		
		// given
		String nome = "";
		LocalDate dataInizio = LocalDate.of(2025, 1, 1);
		LocalDate dataFine = LocalDate.of(2025, 1, 14);
		
		// when then
		assertThrows(ConstraintViolationException.class, () -> TestHelperFilm.createFilm(entityManager, nome, 
				dataInizio, dataFine, 
				tecnologia, sala));
	}
	
	@Test
	public void givenNullDataInizio_whenCreated_thenThrowsException() {
		
		// given
		String nome = "Il dottor Stranamore";
		LocalDate dataInizio = null;
		LocalDate dataFine = LocalDate.of(2025, 1, 14);
		
		// when then
		assertThrows(ConstraintViolationException.class, () -> TestHelperFilm.createFilm(entityManager, nome, 
				dataInizio, dataFine, 
				tecnologia, sala));
	}
	
	@Test
	public void givenNullDataFine_whenCreated_thenThrowsException() {
		
		// given
		String nome = "Il dottor Stranamore";
		LocalDate dataInizio = LocalDate.of(2025, 1, 14);
		LocalDate dataFine = null;
		
		// when then
		assertThrows(ConstraintViolationException.class, () -> TestHelperFilm.createFilm(entityManager, nome, 
				dataInizio, dataFine, 
				tecnologia, sala));
	}
	
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
				LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 14), 
				tecnologia, sala);
		
		// su ricerca il 2025-01-22: passato
		TestHelperFilm.createFilm(entityManager, "2001: odissea nello spazio", 
				LocalDate.of(2025, 1, 8), LocalDate.of(2025, 1, 21), 
				tecnologia, sala);
		
		// su ricerca il 2025-01-22: passato
		TestHelperFilm.createFilm(entityManager, "Barry Lyndon", 
				LocalDate.of(2025, 1, 15), LocalDate.of(2025, 1, 21), 
				tecnologia, sala);
		
		// su ricerca il 2025-01-22: in programmazione
		TestHelperFilm.createFilm(entityManager, "Arancia meccanica", 
				LocalDate.of(2025, 1, 8), LocalDate.of(2025, 1, 28), 
				tecnologia, sala);
		
		// su ricerca il 2025-01-22: in programmazione
		TestHelperFilm.createFilm(entityManager, "Full Metal Jacket", 
				LocalDate.of(2025, 1, 15), LocalDate.of(2025, 1, 28), 
				tecnologia, sala);
	}
}
