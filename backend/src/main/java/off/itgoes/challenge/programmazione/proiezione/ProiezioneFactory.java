package off.itgoes.challenge.programmazione.proiezione;

import java.util.Optional;

import org.springframework.beans.BeanUtils;

import jakarta.persistence.EntityNotFoundException;
import off.itgoes.challenge.programmazione.film.Film;
import off.itgoes.challenge.programmazione.film.FilmRepository;
import off.itgoes.challenge.programmazione.sala.Sala;
import off.itgoes.challenge.programmazione.sala.SalaRepository;

public class ProiezioneFactory {
	
	public static ProiezioneDto getDtoFromEntity(Proiezione entity) {
		ProiezioneDto dto = new ProiezioneDto();
		
		BeanUtils.copyProperties(entity, dto);
				
		dto.setTitoloFilm(entity.getFilm().getTitolo());
		dto.setIdFilm(entity.getFilm().getId());
		
		dto.setIdSala(entity.getSala().getId());
		dto.setNomeSala(entity.getSala().getNome());
		
		return dto;
	}
	
	public static Proiezione getEntityFromDto(ProiezioneDto dto, 
			FilmRepository filmRepository, SalaRepository salaRepository) {
		
		Proiezione entity = new Proiezione();
		
		BeanUtils.copyProperties(dto, entity);
		
		Optional<Film> filmOpt = filmRepository.findById(dto.getIdFilm());
		if (filmOpt.isEmpty()) {
			throw new EntityNotFoundException(("Impossibile trovare il film di "
					+ "ID: " + dto.getIdFilm())
					+ " e titolo: " + dto.getTitoloFilm());
		}
		entity.setFilm(filmOpt.get());
		
		Optional<Sala> salaOpt = salaRepository.findById(dto.getIdSala());
		if (salaOpt.isEmpty()) {
			throw new EntityNotFoundException(("Impossibile trovare la sala di "
					+ "ID: " + dto.getIdSala())
					+ " e nome: " + dto.getNomeSala());
		}
		entity.setSala(salaOpt.get());
		
		return entity;
	}
}
