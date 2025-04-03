import { TestBed } from '@angular/core/testing';

import { InProgrammazioneService } from './in-programmazione.service';

describe('InProgrammazioneService', () => {
  let service: InProgrammazioneService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InProgrammazioneService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
