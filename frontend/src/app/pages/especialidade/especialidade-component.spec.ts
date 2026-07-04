import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EspecialidadeComponent } from './especialidade-component';

describe('EspecialidadeComponent', () => {
  let component: EspecialidadeComponent;
  let fixture: ComponentFixture<EspecialidadeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EspecialidadeComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(EspecialidadeComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
