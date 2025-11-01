import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistoComponent } from './registo.component';

describe('RegistoComponent', () => {
  let component: RegistoComponent;
  let fixture: ComponentFixture<RegistoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegistoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
