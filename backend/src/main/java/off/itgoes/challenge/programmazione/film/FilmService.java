package off.itgoes.challenge.programmazione.film;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import off.itgoes.challenge.programmazione.sala.SalaRepository;
import off.itgoes.challenge.programmazione.tecnologia.TecnologiaRepository;

@Service
@RequiredArgsConstructor
public class FilmService {

	private final TecnologiaRepository tecnologiaRepository;
	private final SalaRepository salaRepository;
	private final FilmRepository filmRepository;
	
	private final FilmBusinessLogic filmBusinessLogic;
		
	public Film createFilm(FilmDto filmDto) {
		
		Film entity = FilmFactory.getEntityFromDto(filmDto, tecnologiaRepository, salaRepository);		
		
		filmBusinessLogic.validaDateFilm(entity);
		filmBusinessLogic.validaTecnologiaFilmVsSala(entity);
		
		Film savedEntity = filmRepository.save(entity);
		
		return savedEntity;
	}
	
	public List<FilmDto> getFilmsInProgrammazione() {
		LocalDate now = LocalDate.now();
		
		List<Film> entities = filmRepository
				.findAllByInizioProgrammazioneLessThanEqualAndFineProgrammazioneGreaterThanEqual(now, now);
		
		List<FilmDto> filmsInProgrammazione = entities.stream()
				.map(entity -> FilmFactory.getDtoFromEntity(entity))
				.collect(Collectors.toList());
		
		return filmsInProgrammazione;
	}
	
	public List<FilmDto> getStoricoFilmsPassati() {
		LocalDate now = LocalDate.now();
		
		List<Film> entities = filmRepository.findAllByFineProgrammazioneBefore(now);
		
		List<FilmDto> filmsPassati = entities.stream()
				.map(entity -> FilmFactory.getDtoFromEntity(entity))
				.collect(Collectors.toList());
		
		return filmsPassati;
	}
}
