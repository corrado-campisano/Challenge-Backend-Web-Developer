package off.itgoes.challenge.databaseTests.tecnologia;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import off.itgoes.challenge.programmazione.tecnologia.Tecnologia;

public class TestHelperTecnologia {
	
	public static Tecnologia creaTecnologia(TestEntityManager entityManager, String nome) {
		
		Tecnologia entity = new Tecnologia();
		
		entity.setNome(nome);
		
		Tecnologia savedEntity = entityManager.persist(entity);
		
		return savedEntity;
	}
}