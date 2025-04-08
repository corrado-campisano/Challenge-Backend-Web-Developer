import { Component, inject, WritableSignal, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';

import { Film } from '../model/film.type';

import { InProgrammazioneService } from '../services/InProgrammazione.service';

@Component({
  selector: 'app-current',
  standalone: true,
  imports: [CommonModule, MatTableModule],
  templateUrl: './current.component.html',
  styleUrl: './current.component.css'
})

export class CurrentComponent {
	
	inProgrammazioneService: InProgrammazioneService = inject(InProgrammazioneService);
	
	displayedColumns: string[] = ['titoloFilm', 'inizioProgrammazione', 'fineProgrammazione', 'nomeSala'];
	dataSource: WritableSignal<Film[]> = signal([]);
	
	ngOnInit() {
		this.inProgrammazioneService.getCurrentFilms().then(
			(filmListResult: Film[]) => {
				this.dataSource.set(filmListResult);
			}
		);
	}
}
