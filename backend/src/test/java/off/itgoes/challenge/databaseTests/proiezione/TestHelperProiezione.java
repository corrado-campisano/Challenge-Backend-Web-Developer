package off.itgoes.challenge.databaseTests.proiezione;

import java.time.LocalDate;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import off.itgoes.challenge.programmazione.film.Film;
import off.itgoes.challenge.programmazione.proiezione.Proiezione;
import off.itgoes.challenge.programmazione.sala.Sala;

public class TestHelperProiezione {

	public static Proiezione createProiezione(TestEntityManager entityManager, 
			LocalDate dataInizio, LocalDate dataFine, Film film, Sala sala) {
		
		Proiezione entity = new Proiezione();
		
		entity.setFilm(film);
		entity.setInizioProgrammazione(dataInizio);
		entity.setFineProgrammazione(dataFine);
		entity.setSala(sala);
		
		Proiezione savedEntity = saveEntity(entityManager, entity);
		
		return savedEntity;
	}

	private static Proiezione saveEntity(TestEntityManager entityManager, Proiezione entity) {
		
		entityManager.persist(entity);
		entityManager.flush();
		
		Proiezione savedEntity = entityManager.refresh(entity);
		
		return savedEntity;
	}
}
