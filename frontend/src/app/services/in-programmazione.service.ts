import { Injectable } from '@angular/core';
import { signal } from '@angular/core';
import { Film } from '../model/film.type';

@Injectable({
  providedIn: 'root'
})
export class InProgrammazioneService {

	filmsSignal = signal<Array<Film>>([]);
	
	constructor() { }
	
	ngOnInit() {
		
	}
}
