package off.itgoes.challenge.programmazione;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FilmController {

	private final FilmService filmService;
	
	@GetMapping("/filmsInProgrammazione")
	public ResponseEntity<List<FilmDto>> getFilmsInProgrammazione(
			@RequestParam(value = "inizioProgrammazione") LocalDate inizioProgrammazione,
			@RequestParam(value = "fineProgrammazione") LocalDate fineProgrammazione) {
		
		List<FilmDto> lista = filmService.getFilmsInProgrammazione(inizioProgrammazione, fineProgrammazione);
		
		if (lista==null || lista.isEmpty()) {
			//return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		}
	}	

	@GetMapping("/storicoFilmsPassati")
	public ResponseEntity<List<FilmDto>> getStoricoFilmsPassati() {
		
		List<FilmDto> lista = filmService.getStoricoFilmsPassati();
		
		if (lista==null || lista.isEmpty()) {
			//return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		}
	}
}
