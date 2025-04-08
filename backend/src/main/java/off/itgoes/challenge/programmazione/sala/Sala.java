package off.itgoes.challenge.programmazione.sala;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import off.itgoes.challenge.programmazione.proiezione.Proiezione;
import off.itgoes.challenge.programmazione.tecnologia.Tecnologia;

@Entity
@Getter @Setter
public class Sala {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotEmpty(message = "Il nome della sala non puo' essere vuoto")
	private String nome;
	
	@Min(value = 50, message = "Il numero di posti non puo' essere inferiore a 50")
	@Max(value = 250, message = "Il numero di posti non puo' essere superiore a 250")
	private Long posti;
	
	@ManyToOne
    @JoinColumn(name="tecnologia_id")
	@NotNull(message = "La tecnologia di proiezione non puo' essere vuota")
    private Tecnologia tecnologiaSala;
	
	@OneToMany(mappedBy="sala")
    private Set<Proiezione> proiezioni;	
}
