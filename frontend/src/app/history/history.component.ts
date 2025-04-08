import { Component, inject, WritableSignal, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';

import { Film } from '../model/film.type';

import { StoricoProgrammazioneService } from '../services/StoricoProgrammazione.service';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [CommonModule, MatTableModule],
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})

export class HistoryComponent {

	storicoProgrammazioneService: StoricoProgrammazioneService = inject(StoricoProgrammazioneService);
	
	displayedColumns: string[] = ['titoloFilm', 'inizioProgrammazione', 'fineProgrammazione', 'nomeSala'];
	dataSource: WritableSignal<Film[]> = signal([]);
	
	ngOnInit() {
		this.storicoProgrammazioneService.getStoricoFilms().then(
			(filmListResult: Film[]) => {
				this.dataSource.set(filmListResult);
			}
		);
	}
}
