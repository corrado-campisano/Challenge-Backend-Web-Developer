package off.itgoes.challenge.programmazione.film;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import off.itgoes.challenge.programmazione.proiezione.ProiezioneDto;
import off.itgoes.challenge.programmazione.proiezione.ProiezioneService;

@RestController
@RequiredArgsConstructor
public class FilmController {

	private final ProiezioneService proiezioneService;
	
	@GetMapping("/filmsInProgrammazione/")
	public ResponseEntity<List<ProiezioneDto>> getFilmsInProgrammazione() {
		
		List<ProiezioneDto> lista = proiezioneService.getFilmsInProgrammazione();
		
		if (lista==null || lista.isEmpty()) {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		}
	}	

	@GetMapping("/storicoFilmsPassati/")
	public ResponseEntity<List<ProiezioneDto>> getStoricoFilmsPassati() {
		
		List<ProiezioneDto> lista = proiezioneService.getStoricoFilmsPassati();
		
		if (lista==null || lista.isEmpty()) {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		}
	}
}
