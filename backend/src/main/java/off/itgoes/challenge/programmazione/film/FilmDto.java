package off.itgoes.challenge.programmazione.film;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FilmDto {

	private Long id;
	
	private String titolo;
	
	private LocalDate inizioProgrammazione;
	
	private LocalDate fineProgrammazione;
	
	private String salaDiProiezione;
}
