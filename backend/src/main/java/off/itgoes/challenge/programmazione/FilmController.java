package off.itgoes.challenge.programmazione;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FilmController {

	private final FilmService filmService;
	
	@GetMapping("/filmsInProgrammazione/")
	public ResponseEntity<List<FilmDto>> getFilmsInProgrammazione() {
		
		List<FilmDto> lista = filmService.getFilmsInProgrammazione();
		
		if (lista==null || lista.isEmpty()) {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		}
	}	

	@GetMapping("/storicoFilmsPassati/")
	public ResponseEntity<List<FilmDto>> getStoricoFilmsPassati() {
		
		List<FilmDto> lista = filmService.getStoricoFilmsPassati();
		
		if (lista==null || lista.isEmpty()) {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		}
	}
}
