import { Component, Injectable, output } from '@angular/core';
import { Subject, combineLatest } from 'rxjs';
import { map } from 'rxjs/operators';
import { FormGroup, FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDatepickerModule, MatDatepickerInputEvent } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { provideNativeDateAdapter } from '@angular/material/core';

import { DateRange } from '../../model/dateRange.type';

@Injectable({
	providedIn: 'root'
})

@Component({
  selector: 'app-date-range-picker',
  standalone: true,
  providers: [provideNativeDateAdapter()],
  imports: [MatFormFieldModule, MatDatepickerModule, FormsModule, ReactiveFormsModule],
  templateUrl: './date-range-picker.component.html',
  styleUrl: './date-range-picker.component.css'
})

export class DateRangePickerComponent {
	
	range = new FormGroup({
	    start: new FormControl<Date | null>(null),
	    end: new FormControl<Date | null>(null),
	});
	
	startDatePicker = new Subject<MatDatepickerInputEvent<any>>();
	endDatePicker = new Subject<MatDatepickerInputEvent<any>>();
	
	dateChanged = output<DateRange>();
	
	ngOnInit(): void {
	    const dateChange$ = combineLatest([this.startDatePicker, this.endDatePicker]).pipe(
			map(([a$, b$]) => ({
	        	start: a$,
	        	end: b$
	      	}))
	    );

	    dateChange$.subscribe((data) => {
			if (data.start.value && data.end.value) {
	        	console.log('User has picked both ranges, forwarding event...');
	        	this.dateChanged.emit({
	        		start: data.start.value, 
	        		end: data.end.value,
	        		valid: true,
	        	});
			} else {
				console.log('One or more ranges missing, forwarding event...');
				this.dateChanged.emit({
	        		start: data.start.value, 
	        		end: data.end.value,
	        		valid: false,
	        	});
	     	}
	    });
	}
}