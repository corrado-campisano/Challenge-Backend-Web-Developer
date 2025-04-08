package off.itgoes.challenge.programmazione.proiezione;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProiezioneService {

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
}
