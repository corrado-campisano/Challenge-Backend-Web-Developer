package off.itgoes.challenge.programmazione;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilmService {

	private final FilmRepository filmRepository;
	
	public Film validateAndCreateFilm(FilmDto film) {
		
		// validazione: massimo tre settimane, minimo una
		Period durataProgrammazione = Period
				.between(film.getInizioProgrammazione(), film.getFineProgrammazione())
				.plusDays(1);
		
		if (durataProgrammazione.getDays()<7 || durataProgrammazione.getDays()>21) {
			throw new FilmPeriodException("La durata di programmazione del film e' da 1 a 3 settimane");
		}
		
		Film entity = new Film();
		
		entity.setTitolo(film.getTitolo());
		entity.setInizioProgrammazione(film.getInizioProgrammazione());
		entity.setFineProgrammazione(film.getFineProgrammazione());
		
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
