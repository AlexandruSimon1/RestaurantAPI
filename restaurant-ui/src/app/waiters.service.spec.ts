import { TestBed } from '@angular/core/testing';

import { WaitersService } from './waiters.service';

describe('WaitersService', () => {
  let service: WaitersService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WaitersService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
