package off.itgoes.challenge.programmazione.film;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FilmDto {

	private Long id;
	
	private String titolo;
	
	private String nomeTecnologia;
	private Long idTecnologia;
}
