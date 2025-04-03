import { Injectable } from '@angular/core';
import { Film } from '../model/film.type';

@Injectable({
  providedIn: 'root'
})

export class InProgrammazioneService {

	url = 'http://localhost:8080/filmsInProgrammazione/';
	
	async getCurrent(): Promise<Array<Film>> {
		const data = await fetch(this.url);
		
		return await data.json() ?? [];
	}
}
