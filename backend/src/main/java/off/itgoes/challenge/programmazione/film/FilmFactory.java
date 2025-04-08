package off.itgoes.challenge.programmazione.film;

import java.util.Optional;

import org.springframework.beans.BeanUtils;

import jakarta.persistence.EntityNotFoundException;
import off.itgoes.challenge.programmazione.tecnologia.Tecnologia;
import off.itgoes.challenge.programmazione.tecnologia.TecnologiaRepository;

public class FilmFactory {

	public static FilmDto getDtoFromEntity(Film entity) {
		FilmDto dto = new FilmDto();
		
		BeanUtils.copyProperties(entity, dto);
				
		dto.setNomeTecnologia(entity.getTecnologiaFilm().getNome());
		dto.setIdTecnologia(entity.getTecnologiaFilm().getId());
		
		return dto;
	}
	
	public static Film getEntityFromDto(FilmDto dto, TecnologiaRepository tecnologiaRepository) {
		
		Film entity = new Film();
		
		BeanUtils.copyProperties(dto, entity);
		
		Optional<Tecnologia> tecnologiaOpt = tecnologiaRepository.findById(dto.getIdTecnologia());
		if (tecnologiaOpt.isEmpty()) {
			throw new EntityNotFoundException(("Impossibile trovare la tecnologia di "
					+ "ID: " + dto.getIdTecnologia())
					+ " e nome: " + dto.getNomeTecnologia());
		}
		entity.setTecnologiaFilm(tecnologiaOpt.get());
				
		return entity;
	}
}
