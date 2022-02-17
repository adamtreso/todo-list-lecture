import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WorktimeRegisterComponent } from './worktime-register.component';

describe('WorktimeRegisterComponent', () => {
  let component: WorktimeRegisterComponent;
  let fixture: ComponentFixture<WorktimeRegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WorktimeRegisterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WorktimeRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
