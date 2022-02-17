import { TestBed } from '@angular/core/testing';

import { WorkLogEntryService } from './work-log-entry.service';

describe('WorkLogEntryService', () => {
  let service: WorkLogEntryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WorkLogEntryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
