package off.itgoes.challenge.databaseTests.tecnologia;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import jakarta.validation.ConstraintViolationException;
import off.itgoes.challenge.programmazione.tecnologia.Tecnologia;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TecnologiaDatabaseTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void givenNomeNull_whenCreated_thenThrowsException() {
		
		// given
		String name = null;
		
		// when then
		assertThrows(ConstraintViolationException.class, () -> TestHelperTecnologia.creaTecnologia(entityManager, name));
	}
	
	@Test
	public void givenNomeEmpty_whenCreated_thenThrowsException() {
		
		// given
		String name = "";
		
		// when then
		assertThrows(ConstraintViolationException.class, () -> TestHelperTecnologia.creaTecnologia(entityManager, name));
	}
	
	@Test
	public void givenValidName_whenCreated_thenCreatedOk() {
		
		// given
		String name = "IMAX";
		
		// when
		Tecnologia savedEntity = TestHelperTecnologia.creaTecnologia(entityManager, name);
		
		// then
		assertTrue(savedEntity.getId() > 0);
	}
}
