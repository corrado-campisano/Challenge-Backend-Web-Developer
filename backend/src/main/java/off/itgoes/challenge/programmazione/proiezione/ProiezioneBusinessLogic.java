package off.itgoes.challenge.programmazione.proiezione;

import java.time.LocalDate;
import java.time.Period;

import off.itgoes.challenge.programmazione.film.exceptions.FilmPeriodException;
import off.itgoes.challenge.programmazione.film.exceptions.FilmTecnologyException;
import off.itgoes.challenge.programmazione.sala.SalaRepository;
import off.itgoes.challenge.programmazione.tecnologia.Tecnologia;

public class ProiezioneBusinessLogic {

	public void validaDateFilm(Proiezione proiezione) {
		// validazione: massimo tre settimane, minimo una
		
		Period durataProgrammazione = Period
				.between(proiezione.getInizioProgrammazione(), proiezione.getFineProgrammazione())
				.plusDays(1);
		
		if (durataProgrammazione.getDays()<7 || durataProgrammazione.getDays()>21) {
			throw new FilmPeriodException("La durata di programmazione del film e' da 1 a 3 settimane");
		}
	}
	
	public void validaTecnologiaFilmVsSala(Proiezione proiezione) {
		// la tecnologia della sala e quella del film devono coincidere
		
		Tecnologia tecnologiaFilm = proiezione.getFilm().getTecnologiaFilm();
		Tecnologia tecnologiaSala = proiezione.getSala().getTecnologiaSala();
		
		if (tecnologiaFilm.getId() != tecnologiaSala.getId()) {
			throw new FilmTecnologyException("La tecnologia della sala "
					+ "(ID: " + tecnologiaSala.getId() + ", "
					+ "nome:'" + tecnologiaSala.getNome() + "') "
					+ "non e' compatibile con la tecnologia del film "
					+ "(ID: " + tecnologiaFilm.getId() + ", "
					+ "nome:'" + tecnologiaFilm.getNome() + "')");
		}
	}
	
	public void validaSalaLiberaNelPeriodo(Proiezione proiezione, SalaRepository salaRepository) {
		LocalDate inizioProgrammazioneFilm = proiezione.getInizioProgrammazione();
		LocalDate fineProgrammazioneFilm = proiezione.getFineProgrammazione();
		
		// TODO : spostare le date nell'entity "Proiezione" e rivedere i test
	}
}
