package off.itgoes.challenge.programmazione.tecnologia;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import off.itgoes.challenge.programmazione.film.Film;
import off.itgoes.challenge.programmazione.sala.Sala;

@Entity
@Getter @Setter
public class Tecnologia {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotEmpty(message = "Il nome della tecnologia non puo' essere vuoto")
	private String nome;
	
	@OneToMany(mappedBy="tecnologiaFilm")
    private Set<Film> films;
	
	@OneToMany(mappedBy="tecnologiaSala")
    private Set<Sala> sale;
}
