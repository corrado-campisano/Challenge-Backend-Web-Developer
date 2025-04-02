import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { CurrentComponent } from './current/current.component';
import { HistoryComponent } from './history/history.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: 'Challenge - Home page'
  },
  {
    path: 'inProgrammazione',
    component: CurrentComponent,
    title: 'Challenge - Programmazione Corrente'
  },
  {
	path: 'storico',
	component: HistoryComponent,
	title: 'Challenge - Storico Programmazione'
  }
];
