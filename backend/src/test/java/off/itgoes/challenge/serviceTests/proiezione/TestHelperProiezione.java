package off.itgoes.challenge.serviceTests.proiezione;

import java.time.LocalDate;

import off.itgoes.challenge.programmazione.film.Film;
import off.itgoes.challenge.programmazione.film.FilmRepository;
import off.itgoes.challenge.programmazione.proiezione.Proiezione;
import off.itgoes.challenge.programmazione.proiezione.ProiezioneDto;
import off.itgoes.challenge.programmazione.proiezione.ProiezioneService;
import off.itgoes.challenge.programmazione.sala.Sala;
import off.itgoes.challenge.programmazione.sala.SalaRepository;

public class TestHelperProiezione {

	public static Proiezione createProiezione(ProiezioneService proiezioneService, 
			ProiezioneDto dto, FilmRepository filmRepository, SalaRepository salaRepository) {
		
		Proiezione proiezione = proiezioneService.createProiezione(dto, filmRepository, salaRepository);
		
		return proiezione;
	}
	
	public static ProiezioneDto createProiezioneDto(
			LocalDate inizioProgrammazione, LocalDate fineProgrammazione,
			Film savedStranamore, Sala savedSalaEntity) {
		
		ProiezioneDto proiezioneDto = new ProiezioneDto();
		
		proiezioneDto.setInizioProgrammazione(inizioProgrammazione);
		proiezioneDto.setFineProgrammazione(fineProgrammazione);
		
		proiezioneDto.setIdFilm(savedStranamore.getId());
		proiezioneDto.setTitoloFilm(savedStranamore.getTitolo());
		
		proiezioneDto.setIdSala(savedSalaEntity.getId());
		proiezioneDto.setNomeSala(savedSalaEntity.getNome());
		
		return proiezioneDto;
	}
}
