package off.itgoes.challenge.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import off.itgoes.challenge.ConstantsTestHelper;
import off.itgoes.challenge.programmazione.film.Film;
import off.itgoes.challenge.programmazione.film.FilmDto;
import off.itgoes.challenge.programmazione.film.FilmService;
import off.itgoes.challenge.programmazione.film.exceptions.FilmPeriodException;
import off.itgoes.challenge.programmazione.film.exceptions.FilmTecnologyException;
import off.itgoes.challenge.programmazione.sala.Sala;
import off.itgoes.challenge.programmazione.sala.SalaRepository;
import off.itgoes.challenge.programmazione.tecnologia.Tecnologia;
import off.itgoes.challenge.programmazione.tecnologia.TecnologiaRepository;

@SpringBootTest
@Transactional
/** implementa i test per:
 *  - validazione constraint a livello di business logic (se presenti)
 *  - validazione query a livello di service (se presenti)
 */
public class FilmServiceTests {

	@Autowired
	private FilmService filmService;
	
	@Autowired
	TecnologiaRepository tecnologiaRepository;
	
	@Autowired
	SalaRepository salaRepository;

	private Tecnologia savedTecnologiaEntity = null;
	private Sala savedSalaEntity = null;
	
	private Tecnologia savedIncompatibileTecnologiaEntity = null;
	
	@BeforeEach
	public void creaTecnologiaAndSala() {
		
		// tecnologia compatibile
		Tecnologia tecnologiaEntity = new Tecnologia();
		tecnologiaEntity.setNome("IMAX");
		savedTecnologiaEntity = tecnologiaRepository.save(tecnologiaEntity);
		
		Sala salaEntity = new Sala();
		salaEntity.setTecnologiaSala(savedTecnologiaEntity);
		salaEntity.setNome("sala 1");
		salaEntity.setPosti(ConstantsTestHelper.NUMERO_MINIMO_POSTI);
		savedSalaEntity = salaRepository.save(salaEntity );
		
		// tecnologia incompatibile
		Tecnologia differentTecnologiaEntity = new Tecnologia();
		differentTecnologiaEntity.setNome("IMAX");
		savedIncompatibileTecnologiaEntity = tecnologiaRepository.save(differentTecnologiaEntity);
		
	}
		
	@Test
	public void givenPeriodoProgrammazioneMenoDiUnaSettimana_whenCreated_thenThrowsException() {

		// given
				String titolo = "titolo";
		LocalDate inizioProgrammazione = LocalDate.of(2025, 01, 10);
		LocalDate fineProgrammazione = LocalDate.of(2025, 01, 15);

		FilmDto filmDto = TestHelperFilm.createFilmDto(titolo, 
				inizioProgrammazione, fineProgrammazione,
				savedTecnologiaEntity, savedSalaEntity);

		// when + then
		assertThrows(FilmPeriodException.class, () -> filmService.createFilm(filmDto));
	}

	@Test
	public void givenPeriodoProgrammazionePiuDiTreSettimane_whenCreated_thenThrowsException() {

		// given
		String titolo = "titolo";
		LocalDate inizioProgrammazione = LocalDate.of(2025, 01, 1);
		LocalDate fineProgrammazione = LocalDate.of(2025, 01, 22);

		FilmDto filmDto = TestHelperFilm.createFilmDto(titolo, 
				inizioProgrammazione, fineProgrammazione,
				savedTecnologiaEntity, savedSalaEntity);

		// when + then
		assertThrows(FilmPeriodException.class, () -> filmService.createFilm(filmDto));
	}

	@Test
	public void givenPeriodoProgrammazioneCorretto_whenCreated_thenSavedOk() {

		// given
		String titolo = "titolo";
		LocalDate inizioProgrammazione = LocalDate.of(2025, 01, 1);
		LocalDate fineProgrammazione = LocalDate.of(2025, 01, 14);

		FilmDto filmDto = TestHelperFilm.createFilmDto(titolo, 
				inizioProgrammazione, fineProgrammazione,
				savedTecnologiaEntity, savedSalaEntity);

		// when
		Film entity = filmService.createFilm(filmDto);

		// then
		assertTrue(entity.getId() > 0);
	}

	@Test
	public void givenTecnologieIncompatibili_whenCreated_thenThrowsException() {
		// given
		String titolo = "titolo";
		LocalDate inizioProgrammazione = LocalDate.of(2025, 01, 1);
		LocalDate fineProgrammazione = LocalDate.of(2025, 01, 14);

		FilmDto filmDto = TestHelperFilm.createFilmDto(titolo, 
				inizioProgrammazione, fineProgrammazione,
				savedIncompatibileTecnologiaEntity, savedSalaEntity);

		// when + then
		assertThrows(FilmTecnologyException.class, () -> filmService.createFilm(filmDto));
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

	private void creaInsiemeDiFilmPerTestRicerca() {
				
		// qui utilizzo date "dinamiche", come richiesto dal service,
		// ma con i valori replicati da quelle "statiche" del test sul repository:
		// vedere metodo analogo nella classe "FilmRepositoryTests"
		// del package "off.itgoes.challenge.repositoryTests" 
		
		// su ricerca il 2025-01-22: passato
		TestHelperFilm.createFilm(filmService, "Il dottor Stranamore", 
				LocalDate.now().minusDays(21),	// era 1	= 22 - 21
				LocalDate.now().minusDays(8),	// era 14	= 22 - 8
				savedTecnologiaEntity, savedSalaEntity);

		// su ricerca il 2025-01-22: passato
		TestHelperFilm.createFilm(filmService, "2001: odissea nello spazio", 
				LocalDate.now().minusDays(14),	// era 8	= 22 - 14
				LocalDate.now().minusDays(1),	// era 21	= 22 - 1
				savedTecnologiaEntity, savedSalaEntity);

		// su ricerca il 2025-01-22: passato
		TestHelperFilm.createFilm(filmService, "Barry Lyndon", 
				LocalDate.now().minusDays(7),	// era 15	= 22 - 7
				LocalDate.now().minusDays(1),	// era 21	= 22 - 1
				savedTecnologiaEntity, savedSalaEntity);

		// su ricerca il 2025-01-22: in programmazione
		TestHelperFilm.createFilm(filmService, "Arancia meccanica", 
				LocalDate.now().minusDays(14),	// era 8	= 22 - 14
				LocalDate.now().plusDays(6),	// era 28	= 22 + 6
				savedTecnologiaEntity, savedSalaEntity);
		
		// su ricerca il 2025-01-22: in programmazione
		TestHelperFilm.createFilm(filmService, "Full Metal Jacket", 
				LocalDate.now().minusDays(7),	// era 15	= 22 - 7
				LocalDate.now().plusDays(6),	// era 28	= 22 + 6
				savedTecnologiaEntity, savedSalaEntity);
	}
}
