package off.itgoes.challenge.programmazione.proiezione;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import off.itgoes.challenge.programmazione.proiezione.exceptions.ProiezionePeriodException;
import off.itgoes.challenge.programmazione.proiezione.exceptions.ProiezioneTecnologyException;
import off.itgoes.challenge.programmazione.tecnologia.Tecnologia;

@Service
@CommonsLog
public class ProiezioneBusinessLogic {

	public void validaDateFilm(Proiezione proiezione) {
		// validazione: massimo tre settimane, minimo una
		
		Period durataProgrammazione = Period
				.between(proiezione.getInizioProgrammazione(), proiezione.getFineProgrammazione())
				.plusDays(1);
		
		if (durataProgrammazione.getDays()<7 || durataProgrammazione.getDays()>21) {
			
			ProiezionePeriodException ex = new ProiezionePeriodException("La durata di programmazione del film e' da 1 a 3 settimane");
			
			log.error("Errore validazione periodo: " + ex.getLocalizedMessage());
			throw ex;
		}
	}
	
	public void validaTecnologiaFilmVsSala(Proiezione proiezione) {
		// la tecnologia della sala e quella del film devono coincidere
		
		Tecnologia tecnologiaFilm = proiezione.getFilm().getTecnologiaFilm();
		Tecnologia tecnologiaSala = proiezione.getSala().getTecnologiaSala();
		
		if (!tecnologiaFilm.getId().equals(tecnologiaSala.getId())) {
			
			ProiezioneTecnologyException ex = new ProiezioneTecnologyException("La tecnologia della sala "
					+ "(ID: " + tecnologiaSala.getId() + ", "
					+ "nome:'" + tecnologiaSala.getNome() + "') "
					+ "non e' compatibile con la tecnologia del film "
					+ "(ID: " + tecnologiaFilm.getId() + ", "
					+ "nome:'" + tecnologiaFilm.getNome() + "')");
			
			log.error("Errore validazione tecnologia: " + ex.getLocalizedMessage());
			throw ex;
		}
	}
	
	public void validaSalaLiberaNelPeriodo(Proiezione proiezione, ProiezioneRepository proiezioneRepository) {
		// una sala non puo' essere occupata da piu' proiezioni nello stesso periodo
		
		LocalDate inizioProgrammazioneFilm = proiezione.getInizioProgrammazione();
		LocalDate fineProgrammazioneFilm = proiezione.getFineProgrammazione();
		
		List<Proiezione> proiezioniStessoPeriodo = proiezioneRepository
				.findAllByInizioProgrammazioneLessThanEqualAndFineProgrammazioneGreaterThanEqual(
						inizioProgrammazioneFilm, fineProgrammazioneFilm);
		
		Predicate<Proiezione> conflictingSalaFilter = item -> 
			item.getSala().getId() == proiezione.getSala().getId();
		
		List<Proiezione> proiezioniStessoPeriodoAndStessaSala = proiezioniStessoPeriodo.stream()
				.filter(conflictingSalaFilter).collect(Collectors.toList());
		
		if (proiezioniStessoPeriodoAndStessaSala!=null && proiezioniStessoPeriodoAndStessaSala.size()>0) {
			
			Proiezione primaProiezioneInConflitto = proiezioniStessoPeriodoAndStessaSala.get(0);
			
			ProiezionePeriodException ex = new ProiezionePeriodException(
					"La sala di ID " + proiezione.getSala().getId() + " e nome '"
							+ proiezione.getSala().getNome() + "' e' gia' occupata "
							+ "dalla proiezione di ID " + primaProiezioneInConflitto.getId() 
							+ " per il film '" + primaProiezioneInConflitto.getFilm().getTitolo() + "'");
			
			log.error("Errore validazione periodo: " + ex.getLocalizedMessage());
			throw ex;
		}
	}
}
