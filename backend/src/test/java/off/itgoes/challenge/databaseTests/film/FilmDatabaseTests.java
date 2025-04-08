package off.itgoes.challenge.databaseTests.film;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import jakarta.validation.ConstraintViolationException;
import off.itgoes.challenge.databaseTests.tecnologia.TestHelperTecnologia;
import off.itgoes.challenge.programmazione.tecnologia.Tecnologia;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
/** implementa i test per:
 *  - validazione constraint a livello di entity (se presenti)
 *  - validazione query a livello di repository (se presenti)
 */
public class FilmDatabaseTests {

	@Autowired
	private TestEntityManager entityManager;
		
	private Tecnologia savedTecnologiaEntity = null;
	
	@BeforeEach
	public void creaTecnologiaAndSala() {
		
		savedTecnologiaEntity = TestHelperTecnologia.creaTecnologia(entityManager, "IMAX");
	}
	
	@Test
	public void givenNullName_whenCreated_thenThrowsException() {
		
		// given
		String titolo = null;
		
		// when then
		assertThrows(ConstraintViolationException.class, () -> TestHelperFilm.createFilm(
				entityManager, titolo, savedTecnologiaEntity));
	}
	
	@Test
	public void givenEmptyName_whenCreated_thenThrowsException() {
		
		// given
		String titolo = "";
		
		// when then
		assertThrows(ConstraintViolationException.class, () -> TestHelperFilm.createFilm(
				entityManager, titolo, savedTecnologiaEntity));
	}
	
	@Test
	public void givenNullTecnologia_whenCreated_thenThrowsException() {
		
		// given
		String titolo = "titolo";
		
		// when then
		assertThrows(ConstraintViolationException.class, () -> TestHelperFilm.createFilm(
				entityManager, titolo, null));
	}
}
