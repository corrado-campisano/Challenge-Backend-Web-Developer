import { Injectable } from '@angular/core';
import { Film } from '../model/film.type';

@Injectable({
  providedIn: 'root'
})

export class InProgrammazioneService {

	url = 'http://localhost:8080/filmsInProgrammazione';
	
	async getCurrentFilms(inizioProgrammazione: string, fineProgrammazione: string): Promise<Array<Film>> {
		const query = this.url + 
				"?inizioProgrammazione=" + inizioProgrammazione + 
				"&fineProgrammazione=" + fineProgrammazione;
		//console.log("Querystring: " + query);
		
		const data = await fetch(query);
				
		return await data.json() ?? [];
	}
}