package off.itgoes.challenge.databaseTests.sala;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import jakarta.validation.ConstraintViolationException;
import off.itgoes.challenge.ConstantsTestHelper;
import off.itgoes.challenge.databaseTests.tecnologia.TestHelperTecnologia;
import off.itgoes.challenge.programmazione.sala.Sala;
import off.itgoes.challenge.programmazione.tecnologia.Tecnologia;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
/** implementa i test per:
 *  - validazione constraint a livello di entity (se presenti)
 *  - validazione query a livello di repository (se presenti)
 */
public class SalaDatabaseTests {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void givenNonExistingTecnologia_whenCreated_thenThrowsException() {

		// given
		Sala entity = new Sala();
		
		entity.setNome("sala 1");
		entity.setPosti(ConstantsTestHelper.NUMERO_MINIMO_POSTI);
		entity.setTecnologiaSala(null);
		
		// when then
		assertThrows(ConstraintViolationException.class, 
				() -> entityManager.persist(entity));
	}
	
	@Test
	public void givenNomeNull_whenCreated_thenThrowsException() {
		// given
		Tecnologia tecnologia = TestHelperTecnologia.creaTecnologia(entityManager, "IMAX");
		
		Sala entity = new Sala();
		
		entity.setNome("");
		entity.setPosti(ConstantsTestHelper.NUMERO_MINIMO_POSTI);
		entity.setTecnologiaSala(tecnologia);
		
		// when then
		assertThrows(ConstraintViolationException.class, () -> entityManager.persist(entity));
	}
	
	@Test
	public void givenNomeVuoto_whenCreated_thenThrowsException() {
		// given
		Tecnologia tecnologia = TestHelperTecnologia.creaTecnologia(entityManager, "IMAX");
		
		Sala entity = new Sala();
		
		entity.setNome("");
		entity.setPosti(ConstantsTestHelper.NUMERO_MINIMO_POSTI);
		entity.setTecnologiaSala(tecnologia);
		
		// when then
		assertThrows(ConstraintViolationException.class, () -> entityManager.persist(entity));
	}
	
	@Test
	public void givenNumeroPostiTroppoBasso_whenCreated_thenThrowsException() {
		// given
		Tecnologia tecnologia = TestHelperTecnologia.creaTecnologia(entityManager, "IMAX");
		
		Sala entity = new Sala();
		
		entity.setNome("sala 1");
		entity.setPosti(ConstantsTestHelper.NUMERO_MINIMO_POSTI - 1);
		entity.setTecnologiaSala(tecnologia);
		
		// when then
		assertThrows(ConstraintViolationException.class, () -> entityManager.persist(entity));
	}
	
	@Test
	public void givenNumeroPostiTroppoAlto_whenCreated_thenThrowsException() {
		// given
		Tecnologia tecnologia = TestHelperTecnologia.creaTecnologia(entityManager, "IMAX");
		
		Sala entity = new Sala();
		
		entity.setNome("sala 1");
		entity.setPosti(ConstantsTestHelper.NUMERO_MASSIMO_POSTI + 1);
		entity.setTecnologiaSala(tecnologia);
		
		// when then
		assertThrows(ConstraintViolationException.class, () -> entityManager.persist(entity));
	}
	
	@Test
	public void givenAllFieldsValid_whenCreated_thenCreatedOk() {
		
		// given
		Tecnologia tecnologia = TestHelperTecnologia.creaTecnologia(entityManager, "IMAX");
		
		Sala savedEntity = TestHelperSala.creaSala(entityManager, "sala 1", tecnologia, ConstantsTestHelper.NUMERO_MINIMO_POSTI);
		
		// then
		assertTrue(savedEntity.getId() > 0);
	}
}
