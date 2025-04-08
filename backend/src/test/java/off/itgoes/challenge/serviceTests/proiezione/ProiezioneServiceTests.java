package off.itgoes.challenge.serviceTests.proiezione;

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
import off.itgoes.challenge.programmazione.film.FilmRepository;
import off.itgoes.challenge.programmazione.proiezione.Proiezione;
import off.itgoes.challenge.programmazione.proiezione.ProiezioneDto;
import off.itgoes.challenge.programmazione.proiezione.ProiezioneService;
import off.itgoes.challenge.programmazione.proiezione.exceptions.ProiezionePeriodException;
import off.itgoes.challenge.programmazione.proiezione.exceptions.ProiezioneTecnologyException;
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
public class ProiezioneServiceTests {

	@Autowired
	TecnologiaRepository tecnologiaRepository;
	
	@Autowired
	SalaRepository salaRepository;
	
	@Autowired
	FilmRepository filmRepository;
	
	@Autowired
	ProiezioneService proiezioneService;
	
	private Tecnologia savedTecnologiaEntity = null;
	private Sala savedSalaUnoEntity = null;
	private Sala savedSalaDueEntity = null;
	private Sala savedSalaTreEntity = null;
	private Sala savedSalaQuattroEntity = null;
	
	private Film savedStranamore = null;
	private Film savedOdissea = null;
	private Film savedBarry = null;
	private Film savedArancia = null;
	private Film savedFmj = null;
	
	private Film savedIncompatibleStranamore = null;
	
	private Tecnologia savedIncompatibileTecnologiaEntity = null;
	
	@BeforeEach
	public void creaTecnologieAndSalaAndFilms() {
		
		// tecnologie
		
		Tecnologia tecnologiaEntity = new Tecnologia();
		tecnologiaEntity.setNome("IMAX");
		savedTecnologiaEntity = tecnologiaRepository.save(tecnologiaEntity);
		
		Tecnologia differentTecnologiaEntity = new Tecnologia();
		differentTecnologiaEntity.setNome("Dolby");
		savedIncompatibileTecnologiaEntity = tecnologiaRepository.save(differentTecnologiaEntity);
		
		// tre sale per evitare sovrapposizioni con le date
		
		Sala salaUnoEntity = new Sala();
		salaUnoEntity.setTecnologiaSala(savedTecnologiaEntity);
		salaUnoEntity.setNome("sala 1");
		salaUnoEntity.setPosti(ConstantsTestHelper.NUMERO_MINIMO_POSTI);
		savedSalaUnoEntity = salaRepository.save(salaUnoEntity);
		
		Sala salaDueEntity = new Sala();
		salaDueEntity.setTecnologiaSala(savedTecnologiaEntity);
		salaDueEntity.setNome("sala 2");
		salaDueEntity.setPosti(ConstantsTestHelper.NUMERO_MINIMO_POSTI);
		savedSalaDueEntity = salaRepository.save(salaDueEntity);
		
		Sala salaTreEntity = new Sala();
		salaTreEntity.setTecnologiaSala(savedTecnologiaEntity);
		salaTreEntity.setNome("sala 3");
		salaTreEntity.setPosti(ConstantsTestHelper.NUMERO_MINIMO_POSTI);
		savedSalaTreEntity = salaRepository.save(salaTreEntity);
		
		Sala salaQuattroEntity = new Sala();
		salaQuattroEntity.setTecnologiaSala(savedTecnologiaEntity);
		salaQuattroEntity.setNome("sala 4");
		salaQuattroEntity.setPosti(ConstantsTestHelper.NUMERO_MINIMO_POSTI);
		savedSalaQuattroEntity = salaRepository.save(salaQuattroEntity);
		
		// films
		
		Film stranamore = new Film();
		stranamore.setTecnologiaFilm(tecnologiaEntity);
		stranamore.setTitolo("Il dottor Stranamore");
		savedStranamore = filmRepository.save(stranamore);
		
		Film odissea = new Film();
		odissea.setTecnologiaFilm(tecnologiaEntity);
		odissea.setTitolo("2001: odissea nello spazio");
		savedOdissea = filmRepository.save(odissea);
		
		Film barry = new Film();
		barry.setTecnologiaFilm(tecnologiaEntity);
		barry.setTitolo("Barry Lyndon");
		savedBarry = filmRepository.save(barry);
		
		Film arancia = new Film();
		arancia.setTecnologiaFilm(tecnologiaEntity);
		arancia.setTitolo("Arancia meccanica");
		savedArancia = filmRepository.save(arancia);
		
		Film fmj = new Film();
		fmj.setTecnologiaFilm(tecnologiaEntity);
		fmj.setTitolo("Full Metal Jacket");
		savedFmj = filmRepository.save(fmj);
		
		Film incompatibleStranamore = new Film();
		incompatibleStranamore.setTecnologiaFilm(savedIncompatibileTecnologiaEntity);
		incompatibleStranamore.setTitolo("Il dottor Stranamore");
		savedIncompatibleStranamore = filmRepository.save(incompatibleStranamore);
	}
	
	@Test
	public void givenPeriodoProgrammazioneMenoDiUnaSettimana_whenCreated_thenThrowsException() {

		// given
		LocalDate inizioProgrammazione = LocalDate.of(2025, 01, 10);
		LocalDate fineProgrammazione = LocalDate.of(2025, 01, 15);
		
		ProiezioneDto proiezioneDto = TestHelperProiezione.createProiezioneDto(
				inizioProgrammazione, fineProgrammazione,
				savedStranamore, savedSalaUnoEntity);

		// when + then
		assertThrows(ProiezionePeriodException.class, () -> proiezioneService.createProiezione(
				proiezioneDto, filmRepository, salaRepository));
	}

	@Test
	public void givenPeriodoProgrammazionePiuDiTreSettimane_whenCreated_thenThrowsException() {

		// given
		LocalDate inizioProgrammazione = LocalDate.of(2025, 01, 1);
		LocalDate fineProgrammazione = LocalDate.of(2025, 01, 22);

		ProiezioneDto proiezioneDto = TestHelperProiezione.createProiezioneDto(
				inizioProgrammazione, fineProgrammazione,
				savedStranamore, savedSalaUnoEntity);

		// when + then
		assertThrows(ProiezionePeriodException.class, () -> proiezioneService.createProiezione(
				proiezioneDto, filmRepository, salaRepository));
	}

	@Test
	public void givenPeriodoProgrammazioneCorretto_whenCreated_thenSavedOk() {

		// given
		LocalDate inizioProgrammazione = LocalDate.of(2025, 01, 1);
		LocalDate fineProgrammazione = LocalDate.of(2025, 01, 14);

		ProiezioneDto proiezioneDto = TestHelperProiezione.createProiezioneDto(
				inizioProgrammazione, fineProgrammazione,
				savedStranamore, savedSalaUnoEntity);

		// when
		Proiezione entity = proiezioneService.createProiezione(
				proiezioneDto, filmRepository, salaRepository);

		// then
		assertTrue(entity.getId() > 0);
	}

	@Test
	public void givenTecnologieIncompatibili_whenCreated_thenThrowsException() {
		
		// given
		LocalDate inizioProgrammazione = LocalDate.of(2025, 01, 1);
		LocalDate fineProgrammazione = LocalDate.of(2025, 01, 14);

		ProiezioneDto proiezioneDto = TestHelperProiezione.createProiezioneDto(
				inizioProgrammazione, fineProgrammazione,
				savedIncompatibleStranamore, savedSalaUnoEntity);

		// when + then
		assertThrows(ProiezioneTecnologyException.class, () -> proiezioneService.createProiezione(
				proiezioneDto, filmRepository, salaRepository));
	} 

	@Test
	public void givenDateIncompatibili_whenCreated_thenThrowsException() {
		
		// given
		LocalDate primoInizioProgrammazione = LocalDate.of(2025, 01, 1);
		LocalDate primaFineProgrammazione = LocalDate.of(2025, 01, 14);

		ProiezioneDto primaProiezioneDto = TestHelperProiezione.createProiezioneDto(
				primoInizioProgrammazione, primaFineProgrammazione,
				savedStranamore, savedSalaUnoEntity);
		
		@SuppressWarnings("unused")
		Proiezione primaProiezione = proiezioneService.createProiezione(
				primaProiezioneDto, filmRepository, salaRepository);

		LocalDate secondoInizioProgrammazione = LocalDate.of(2025, 01, 3);
		LocalDate secondaFineProgrammazione = LocalDate.of(2025, 01, 10);
		
		ProiezioneDto secondaProiezioneDto = TestHelperProiezione.createProiezioneDto(
				secondoInizioProgrammazione, secondaFineProgrammazione,
				savedArancia, savedSalaUnoEntity);
		
		// when + then
		assertThrows(ProiezionePeriodException.class, () -> proiezioneService.createProiezione(
				secondaProiezioneDto, filmRepository, salaRepository));
	}
	
	@Test
	public void givenFilmsInProgrammazionePresenti_whenSearched_thenRetrievedOk() {

		// given
		creaInsiemeDiProiezioniPerTestRicerca();
		LocalDate now = LocalDate.now();

		// when
		List<ProiezioneDto> filmsInProgrammazione = proiezioneService.getFilmsInProgrammazione(now, now);

		// then
		assertEquals(2, filmsInProgrammazione.size());
	}

	@Test
	public void givenStoricoFilmsPresente_whenSearched_thenRetrievedOk() {

		// given
		creaInsiemeDiProiezioniPerTestRicerca();

		// when
		List<ProiezioneDto> filmsInProgrammazione = proiezioneService.getStoricoFilmsPassati();

		// then
		assertEquals(3, filmsInProgrammazione.size());
	}

	private void creaInsiemeDiProiezioniPerTestRicerca() {
				
		// qui utilizzo date "dinamiche", come richiesto dal service,
		// ma con i valori replicati da quelle "statiche" del test sul repository:
		// vedere metodo analogo nella classe "FilmRepositoryTests"
		// del package "off.itgoes.challenge.repositoryTests" 
		
		// su ricerca il 2025-01-22: passato
		
		ProiezioneDto stranamoreDto = TestHelperProiezione.createProiezioneDto( 
				LocalDate.now().minusDays(21),	// era 1	= 22 - 21
				LocalDate.now().minusDays(8),	// era 14	= 22 - 8
				savedStranamore, savedSalaUnoEntity);
		TestHelperProiezione.createProiezione(proiezioneService, stranamoreDto, filmRepository, salaRepository);

		// su ricerca il 2025-01-22: passato
		ProiezioneDto odisseaDto = TestHelperProiezione.createProiezioneDto( 
				LocalDate.now().minusDays(14),	// era 8	= 22 - 14
				LocalDate.now().minusDays(1),	// era 21	= 22 - 1
				savedOdissea, savedSalaDueEntity);
		TestHelperProiezione.createProiezione(proiezioneService, odisseaDto, filmRepository, salaRepository);

		// su ricerca il 2025-01-22: passato
		ProiezioneDto barryDto = TestHelperProiezione.createProiezioneDto( 
				LocalDate.now().minusDays(7),	// era 15	= 22 - 7
				LocalDate.now().minusDays(1),	// era 21	= 22 - 1
				savedBarry, savedSalaUnoEntity);
		TestHelperProiezione.createProiezione(proiezioneService, barryDto, filmRepository, salaRepository);
		
		// su ricerca il 2025-01-22: in programmazione
		ProiezioneDto aranciaDto = TestHelperProiezione.createProiezioneDto( 
				LocalDate.now().minusDays(14),	// era 8	= 22 - 14
				LocalDate.now().plusDays(6),	// era 28	= 22 + 6
				savedArancia, savedSalaTreEntity);
		TestHelperProiezione.createProiezione(proiezioneService, aranciaDto, filmRepository, salaRepository);
		
		// su ricerca il 2025-01-22: in programmazione
		ProiezioneDto fmjDto = TestHelperProiezione.createProiezioneDto( 
				LocalDate.now().minusDays(7),	// era 15	= 22 - 7
				LocalDate.now().plusDays(6),	// era 28	= 22 + 6
				savedFmj, savedSalaQuattroEntity);
		TestHelperProiezione.createProiezione(proiezioneService, fmjDto, filmRepository, salaRepository);
	}
}
