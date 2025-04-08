package off.itgoes.challenge.databaseTests.film;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import off.itgoes.challenge.programmazione.film.Film;
import off.itgoes.challenge.programmazione.tecnologia.Tecnologia;

public class TestHelperFilm {

	public static Film createFilm(TestEntityManager entityManager, 
			String titolo, Tecnologia tecnologia) {
		
		Film entity = new Film();
		
		entity.setTitolo(titolo);
		entity.setTecnologiaFilm(tecnologia);
		
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
