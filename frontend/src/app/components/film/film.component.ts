import { Component, Input } from '@angular/core';
import { Film } from '../../model/film.type';

@Component({
  selector: 'app-film',
  standalone: true,
  imports: [],
  templateUrl: './film.component.html',
  styleUrl: './film.component.css'
})
export class FilmComponent {

	@Input() film!: Film;
}
