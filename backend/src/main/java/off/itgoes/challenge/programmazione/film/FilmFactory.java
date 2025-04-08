package off.itgoes.challenge.programmazione.film;

import java.util.Optional;

import org.springframework.beans.BeanUtils;

import jakarta.persistence.EntityNotFoundException;
import off.itgoes.challenge.programmazione.sala.Sala;
import off.itgoes.challenge.programmazione.sala.SalaRepository;
import off.itgoes.challenge.programmazione.tecnologia.Tecnologia;
import off.itgoes.challenge.programmazione.tecnologia.TecnologiaRepository;

public class FilmFactory {

	public static FilmDto getDtoFromEntity(Film entity) {
		FilmDto dto = new FilmDto();
		
		BeanUtils.copyProperties(entity, dto);
		
		dto.setNomeSala(entity.getSala().getNome());
		dto.setIdSala(entity.getSala().getId());
		
		dto.setNomeTecnologia(entity.getTecnologiaFilm().getNome());
		dto.setIdTecnologia(entity.getTecnologiaFilm().getId());
		
		return dto;
	}
	
	public static Film getEntityFromDto(FilmDto dto, 
			TecnologiaRepository tecnologiaRepository, SalaRepository salaRepository) {
		
		Film entity = new Film();
		
		BeanUtils.copyProperties(dto, entity);
		
		Optional<Tecnologia> tecnologiaOpt = tecnologiaRepository.findById(dto.getIdTecnologia());
		if (tecnologiaOpt.isEmpty()) {
			throw new EntityNotFoundException(("Impossibile trovare la tecnologia di "
					+ "ID: " + dto.getIdTecnologia())
					+ " e nome: " + dto.getNomeTecnologia());
		}
		entity.setTecnologiaFilm(tecnologiaOpt.get());
		
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
