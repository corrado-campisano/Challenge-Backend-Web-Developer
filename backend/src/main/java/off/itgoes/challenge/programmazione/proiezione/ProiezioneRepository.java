package off.itgoes.challenge.programmazione.proiezione;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProiezioneRepository extends JpaRepository<Proiezione, Long>{

	List<Proiezione> findAllByInizioProgrammazioneLessThanEqualAndFineProgrammazioneGreaterThanEqual(LocalDate dataInizio, LocalDate dataFine);
	
	List<Proiezione> findAllByFineProgrammazioneBefore(LocalDate dataFine);
}
