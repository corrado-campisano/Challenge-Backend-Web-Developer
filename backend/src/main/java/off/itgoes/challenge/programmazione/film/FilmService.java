package off.itgoes.challenge.programmazione.film;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import off.itgoes.challenge.programmazione.tecnologia.TecnologiaRepository;

@Service
@RequiredArgsConstructor
public class FilmService {

	private final TecnologiaRepository tecnologiaRepository;
	private final FilmRepository filmRepository;
			
	public Film createFilm(FilmDto filmDto) {
		
		Film entity = FilmFactory.getEntityFromDto(filmDto, tecnologiaRepository);		
				
		Film savedEntity = filmRepository.save(entity);
		
		return savedEntity;
	}
}
