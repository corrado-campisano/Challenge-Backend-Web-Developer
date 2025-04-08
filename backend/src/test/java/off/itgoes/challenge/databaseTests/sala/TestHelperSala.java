package off.itgoes.challenge.databaseTests.sala;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import off.itgoes.challenge.programmazione.sala.Sala;
import off.itgoes.challenge.programmazione.tecnologia.Tecnologia;

public class TestHelperSala {
	
	public static Sala creaSala(TestEntityManager entityManager, String nome, Tecnologia tecnologia, Long posti) {
		
		Sala entity = new Sala();
		
		entity.setNome(nome);
		entity.setTecnologiaSala(tecnologia);
		entity.setPosti(posti);
		
		// when 
		Sala savedEntity = entityManager.persist(entity);
		
		return savedEntity;
	}
}
