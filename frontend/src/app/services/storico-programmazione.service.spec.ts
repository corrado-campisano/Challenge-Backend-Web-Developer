import { TestBed } from '@angular/core/testing';

import { StoricoProgrammazioneService } from './storico-programmazione.service';

describe('StoricoProgrammazioneService', () => {
  let service: StoricoProgrammazioneService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StoricoProgrammazioneService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
