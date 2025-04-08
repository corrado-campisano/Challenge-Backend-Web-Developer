package off.itgoes.challenge.programmazione.proiezione;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import off.itgoes.challenge.programmazione.film.FilmRepository;
import off.itgoes.challenge.programmazione.sala.SalaRepository;

@Service
@RequiredArgsConstructor
public class ProiezioneService {

	private final ProiezioneBusinessLogic proiezioneBusinessLogic;
	
	private final ProiezioneRepository proiezioneRepository;
	
	public List<ProiezioneDto> getFilmsInProgrammazione() {
		LocalDate now = LocalDate.now();
		
		List<Proiezione> entities = proiezioneRepository
				.findAllByInizioProgrammazioneLessThanEqualAndFineProgrammazioneGreaterThanEqual(now, now);
		
		List<ProiezioneDto> filmsInProgrammazione = entities.stream()
				.map(entity -> ProiezioneFactory.getDtoFromEntity(entity))
				.collect(Collectors.toList());
		
		return filmsInProgrammazione;
	}
	
	public List<ProiezioneDto> getStoricoFilmsPassati() {
		LocalDate now = LocalDate.now();
		
		List<Proiezione> entities = proiezioneRepository.findAllByFineProgrammazioneBefore(now);
		
		List<ProiezioneDto> filmsPassati = entities.stream()
				.map(entity -> ProiezioneFactory.getDtoFromEntity(entity))
				.collect(Collectors.toList());
		
		return filmsPassati;
	}

	public Proiezione createProiezione(ProiezioneDto proiezioneDto, 
			FilmRepository filmRepository, SalaRepository salaRepository) {
		
		Proiezione proiezione = ProiezioneFactory.getEntityFromDto(proiezioneDto, filmRepository, salaRepository);
		
		proiezioneBusinessLogic.validaDateFilm(proiezione);
		proiezioneBusinessLogic.validaTecnologiaFilmVsSala(proiezione);
		proiezioneBusinessLogic.validaSalaLiberaNelPeriodo(proiezione, proiezioneRepository);
		
		Proiezione savedProiezione = proiezioneRepository.save(proiezione);
		
		return savedProiezione;
	}
}
