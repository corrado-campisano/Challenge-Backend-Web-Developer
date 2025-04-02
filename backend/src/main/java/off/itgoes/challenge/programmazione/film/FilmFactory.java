package off.itgoes.challenge.programmazione.film;

import org.springframework.beans.BeanUtils;

public class FilmFactory {

	public static FilmDto getDtoFromEntity(Film entity) {
		FilmDto dto = new FilmDto();
		
		BeanUtils.copyProperties(entity, dto);
		
		return dto;
	}
	
	public static Film getEntityFromDto(FilmDto dto) {
		Film entity = new Film();
		
		BeanUtils.copyProperties(dto, entity);
		
		return entity;
	}
}
