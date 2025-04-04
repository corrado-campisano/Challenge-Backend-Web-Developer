package off.itgoes.challenge.repositoryTests;

import java.time.LocalDate;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import off.itgoes.challenge.programmazione.Film;

public class TestHelperFilm {

	public static Film createFilm(TestEntityManager entityManager, String titolo, 
			LocalDate inizioProgrammazione, LocalDate fineProgrammazione, String sala) {
		
		Film entity = new Film();
		
		entity.setTitolo(titolo);
		entity.setInizioProgrammazione(inizioProgrammazione);
		entity.setFineProgrammazione(fineProgrammazione);
		entity.setSalaDiProiezione(sala);
		
		Film savedEntity = saveEntity(entityManager, entity);
		
		return savedEntity;
	}

	private static Film saveEntity(TestEntityManager entityManager, Film entity) {
		
		entityManager.persist(entity);
		entityManager.flush();
		
		Film savedEntity = entityManager.refresh(entity);
		
		return savedEntity;
	}
}
