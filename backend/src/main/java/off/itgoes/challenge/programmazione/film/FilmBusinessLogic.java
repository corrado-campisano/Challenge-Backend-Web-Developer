package off.itgoes.challenge.programmazione.film;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Service;

import off.itgoes.challenge.programmazione.film.exceptions.FilmPeriodException;
import off.itgoes.challenge.programmazione.film.exceptions.FilmTecnologyException;
import off.itgoes.challenge.programmazione.sala.SalaRepository;
import off.itgoes.challenge.programmazione.tecnologia.Tecnologia;

@Service
public class FilmBusinessLogic {

	public void validaDateFilm(Film film) {
		// validazione: massimo tre settimane, minimo una
		
		Period durataProgrammazione = Period
				.between(film.getInizioProgrammazione(), film.getFineProgrammazione())
				.plusDays(1);
		
		if (durataProgrammazione.getDays()<7 || durataProgrammazione.getDays()>21) {
			throw new FilmPeriodException("La durata di programmazione del film e' da 1 a 3 settimane");
		}
	}
	
	public void validaTecnologiaFilmVsSala(Film film) {
		// la tecnologia della sala e quella del film devono coincidere
		
		Tecnologia tecnologiaFilm = film.getTecnologiaFilm();
		Tecnologia tecnologiaSala = film.getSala().getTecnologiaSala();
		
		if (tecnologiaFilm.getId() != tecnologiaSala.getId()) {
			throw new FilmTecnologyException("La tecnologia della sala "
					+ "(ID: " + tecnologiaSala.getId() + ", "
					+ "nome:'" + tecnologiaSala.getNome() + "') "
					+ "non e' compatibile con la tecnologia del film "
					+ "(ID: " + tecnologiaFilm.getId() + ", "
					+ "nome:'" + tecnologiaFilm.getNome() + "')");
		}
	}
	
	public void validaSalaLiberaNelPeriodo(Film film, SalaRepository salaRepository) {
		LocalDate inizioProgrammazioneFilm = film.getInizioProgrammazione();
		LocalDate fineProgrammazioneFilm = film.getFineProgrammazione();
		
		// TODO : spostare le date nell'entity "Proiezione" e rivedere i test
	}
}
