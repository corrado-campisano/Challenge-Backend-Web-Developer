package off.itgoes.challenge.programmazione.film;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import off.itgoes.challenge.programmazione.sala.Sala;
import off.itgoes.challenge.programmazione.tecnologia.Tecnologia;

@Entity
@Getter @Setter
public class Film {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotEmpty(message = "Il nome del film non puo' essere vuoto")
	private String titolo;
	
	@NotNull(message = "La data di inizio programmazione non puo' essere vuota")
	private LocalDate inizioProgrammazione;
	
	@NotNull(message = "La data di fine programmazione non puo' essere vuota")
	private LocalDate fineProgrammazione;
	
	@ManyToOne
    @JoinColumn(name="sala_id")
	@NotNull(message = "La sala di proiezione non puo' essere vuota")
	private Sala sala;
	
	@ManyToOne
    @JoinColumn(name="tecnologia_id")
	@NotNull(message = "La tecnologia di proiezione non puo' essere vuota")
    private Tecnologia tecnologiaFilm;
}
