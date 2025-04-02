package off.itgoes.challenge.programmazione;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilmService {

	private final FilmRepository filmRepository;
	
	public List<Film> getFilmsInProgrammazione() {
		LocalDate now = LocalDate.now();
		
		List<Film> filmsInProgrammazione = filmRepository
				.findAllByInizioProgrammazioneLessThanEqualAndFineProgrammazioneGreaterThanEqual(now, now);
		
		return filmsInProgrammazione;
	}
	
	public List<Film> getStoricoFilmsPassati() {
		LocalDate now = LocalDate.now();
		
		List<Film> filmsPassati = filmRepository.findAllByFineProgrammazioneBefore(now);
		
		return filmsPassati;
	}
}
