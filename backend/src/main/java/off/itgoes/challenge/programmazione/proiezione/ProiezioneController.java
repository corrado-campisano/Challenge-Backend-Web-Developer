package off.itgoes.challenge.programmazione.proiezione;

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
public class ProiezioneController {

	private final ProiezioneService proiezioneService;
	
	@GetMapping("/filmsInProgrammazione")
	public ResponseEntity<List<ProiezioneDto>> getFilmsInProgrammazione(
			@RequestParam LocalDate inizioProgrammazione,
			@RequestParam LocalDate fineProgrammazione) {
		
		List<ProiezioneDto> lista = proiezioneService.getFilmsInProgrammazione(inizioProgrammazione, fineProgrammazione);
		
		if (lista==null || lista.isEmpty()) {
			//return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
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
