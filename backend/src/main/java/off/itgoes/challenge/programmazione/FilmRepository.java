package off.itgoes.challenge.programmazione;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long>{

	List<Film> findAllByInizioProgrammazioneLessThanEqualAndFineProgrammazioneGreaterThanEqual(LocalDate dataInizio, LocalDate dataFine);
	
	List<Film> findAllByFineProgrammazioneBefore(LocalDate dataFine);
}
