import { Component, inject, WritableSignal, signal, effect } from '@angular/core';
import { CommonModule, formatDate } from '@angular/common';
import { MatTableModule } from '@angular/material/table';

import { provideNativeDateAdapter } from '@angular/material/core';

import { DateRangePickerComponent } from '../components/date-range-picker/date-range-picker.component';

import { Film } from '../model/film.type';
import { InProgrammazioneService } from '../services/InProgrammazione.service';

import { DateRange } from '../model/dateRange.type';

@Component({
  selector: 'app-current',
  standalone: true,
  providers: [provideNativeDateAdapter()],
  imports: [CommonModule, MatTableModule, DateRangePickerComponent],
  templateUrl: './current.component.html',
  styleUrl: './current.component.css'
})

export class CurrentComponent {
	
	inProgrammazioneService: InProgrammazioneService = inject(InProgrammazioneService);
	
	displayedColumns: string[] = ['titoloFilm', 'inizioProgrammazione', 'fineProgrammazione', 'nomeSala'];
	dataSource: WritableSignal<Film[]> = signal([]);
	
	searchDefault: string = formatDate(Date.now(),'yyyy-MM-dd',"en-US");
	
	searchStart: string = "";
	searchEnd: string = "";
	
	searchDisabled: WritableSignal<boolean> = signal(true);
	
	updateSearchEnabled(range: DateRange) {
		console.log("range: (" + range.valid + ") : " + range.start + ", " + range.end);
		
		this.searchStart = formatDate(range.start,'yyyy-MM-dd',"en-US");;
		this.searchEnd = formatDate(range.end,'yyyy-MM-dd',"en-US"); ;
		
		this.searchDisabled.set(!range.valid);
	}
	
	filteredSearch() {
	
		console.log("doing filtered search...");
		
		this.inProgrammazioneService.getCurrentFilms(this.searchStart, this.searchEnd).then(
			(filmListResult: Film[]) => {
				this.dataSource.set(filmListResult);
			}
		);
	}
	
	todaySearch() {
		this.inProgrammazioneService.getCurrentFilms(this.searchDefault, this.searchDefault).then(
			(filmListResult: Film[]) => {
				this.dataSource.set(filmListResult);
			}
		);
	}
	
	ngOnInit() {
		this.todaySearch();
	}
}
