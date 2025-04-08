package off.itgoes.challenge.programmazione.proiezione;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProiezioneDto {

	private Long id;
	
	private LocalDate inizioProgrammazione;
	
	private LocalDate fineProgrammazione;
	
	private String nomeSala;	
	private Long idSala;
	
	private String titoloFilm;
	private Long idFilm;
	
}
