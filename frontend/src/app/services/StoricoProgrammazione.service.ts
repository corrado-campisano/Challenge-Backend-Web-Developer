import { Injectable } from '@angular/core';
import { Film } from '../model/film.type';

@Injectable({
  providedIn: 'root'
})

export class StoricoProgrammazioneService {

	url = 'http://localhost:8080/storicoFilmsPassati/';
	
	async getStoricoFilms(): Promise<Film[]> {
		const data = await fetch(this.url);
		
		return await data.json() ?? [];
	}
}
