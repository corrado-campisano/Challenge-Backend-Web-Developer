package off.itgoes.challenge.databaseTests.proiezione;

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
import off.itgoes.challenge.databaseTests.film.TestHelperFilm;
import off.itgoes.challenge.databaseTests.sala.TestHelperSala;
import off.itgoes.challenge.databaseTests.tecnologia.TestHelperTecnologia;
import off.itgoes.challenge.programmazione.film.Film;
import off.itgoes.challenge.programmazione.proiezione.Proiezione;
import off.itgoes.challenge.programmazione.proiezione.ProiezioneRepository;
import off.itgoes.challenge.programmazione.sala.Sala;
import off.itgoes.challenge.programmazione.tecnologia.Tecnologia;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
/** implementa i test per:
 *  - validazione constraint a livello di entity (se presenti)
 *  - validazione query a livello di repository (se presenti)
 */
public class ProiezioneDatabaseTests {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	ProiezioneRepository proiezioneRepository;
	
	private Tecnologia savedTecnologiaEntity = null;
	private Sala savedSalaEntity = null;
	
	private Film stranamore = null;
	private Film odissea = null;
	private Film barry = null;
	private Film arancia = null;
	private Film fmj = null;
	
	@BeforeEach
	public void creaTecnologiaAndSala() {
		
		savedTecnologiaEntity = TestHelperTecnologia.creaTecnologia(entityManager, "IMAX");
		
		stranamore = TestHelperFilm.createFilm(entityManager, 
				"Il dottor Stranamore", savedTecnologiaEntity);
		
		odissea = TestHelperFilm.createFilm(entityManager, 
				"2001: odissea nello spazio", savedTecnologiaEntity);
		
		barry = TestHelperFilm.createFilm(entityManager, 
				"Barry Lyndon", savedTecnologiaEntity);
		
		arancia = TestHelperFilm.createFilm(entityManager, 
				"Arancia meccanica", savedTecnologiaEntity);
		
		fmj = TestHelperFilm.createFilm(entityManager, 
				"Full Metal Jacket", savedTecnologiaEntity);
		
		savedSalaEntity = TestHelperSala.creaSala(entityManager, "sala 1", savedTecnologiaEntity, ConstantsTestHelper.NUMERO_MINIMO_POSTI);
	}
	
	@Test
	public void givenNullDataInizio_whenCreated_thenThrowsException() {
		
		// given
		LocalDate dataInizio = null;
		LocalDate dataFine = LocalDate.of(2025, 1, 14);
		
		// when then
		assertThrows(ConstraintViolationException.class, () -> TestHelperProiezione.createProiezione( 
				entityManager, dataInizio, dataFine, stranamore, savedSalaEntity));
	}
	
	@Test
	public void givenNullDataFine_whenCreated_thenThrowsException() {
		
		// given
		LocalDate dataInizio = LocalDate.of(2025, 1, 14);
		LocalDate dataFine = null;
		
		// when then
		assertThrows(ConstraintViolationException.class, () -> TestHelperProiezione.createProiezione( 
				entityManager, dataInizio, dataFine, stranamore, savedSalaEntity));
	}
	
	@Test
	public void givenFilmsInProgrammazionePresenti_whenSearched_thenRetrievedOk() {
		
		// given
		creaInsiemeDiProiezioniPerTestRicerca();
		
		// when
		LocalDate now = LocalDate.of(2025, 01, 22);
		
		List<Proiezione> proiezioni = proiezioneRepository
				.findAllByInizioProgrammazioneLessThanEqualAndFineProgrammazioneGreaterThanEqual(now, now);
		
		// then
		assertEquals(2, proiezioni.size());
	}

	@Test
	public void givenStoricoFilmsPresente_whenSearched_thenRetrievedOk() {
		
		// given
		creaInsiemeDiProiezioniPerTestRicerca();
		
		// when
		LocalDate now = LocalDate.of(2025, 01, 22);
		
		List<Proiezione> proiezioni = proiezioneRepository
				.findAllByFineProgrammazioneBefore(now);
		
		// then
		assertEquals(3, proiezioni.size());
	}
	
	public void creaInsiemeDiProiezioniPerTestRicerca() {
				
		// su ricerca il 2025-01-22: passato
		TestHelperProiezione.createProiezione(entityManager,  
				LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 14), 
				stranamore, savedSalaEntity);
		
		// su ricerca il 2025-01-22: passato
		TestHelperProiezione.createProiezione(entityManager,  
				LocalDate.of(2025, 1, 8), LocalDate.of(2025, 1, 21), 
				odissea, savedSalaEntity);
		
		// su ricerca il 2025-01-22: passato
		TestHelperProiezione.createProiezione(entityManager,  
				LocalDate.of(2025, 1, 15), LocalDate.of(2025, 1, 21), 
				barry, savedSalaEntity);
		
		// su ricerca il 2025-01-22: in programmazione
		TestHelperProiezione.createProiezione(entityManager,  
				LocalDate.of(2025, 1, 8), LocalDate.of(2025, 1, 28), 
				arancia, savedSalaEntity);
		
		// su ricerca il 2025-01-22: in programmazione
		TestHelperProiezione.createProiezione(entityManager,  
				LocalDate.of(2025, 1, 15), LocalDate.of(2025, 1, 28), 
				fmj, savedSalaEntity);
	}		
}
