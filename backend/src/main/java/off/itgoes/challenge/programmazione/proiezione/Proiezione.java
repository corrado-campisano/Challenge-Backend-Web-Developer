package off.itgoes.challenge.programmazione.proiezione;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import off.itgoes.challenge.programmazione.film.Film;
import off.itgoes.challenge.programmazione.sala.Sala;

@Entity
@Getter @Setter
public class Proiezione {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull(message = "La data di inizio programmazione non puo' essere vuota")
	private LocalDate inizioProgrammazione;
	
	@NotNull(message = "La data di fine programmazione non puo' essere vuota")
	private LocalDate fineProgrammazione;

	@ManyToOne
    @JoinColumn(name="sala_id")
	@NotNull(message = "La sala di proiezione non puo' essere vuota")
	private Sala sala;
	
	@ManyToOne
    @JoinColumn(name="film_id")
	@NotNull(message = "La sala di proiezione non puo' essere vuota")
	private Film film;
}
