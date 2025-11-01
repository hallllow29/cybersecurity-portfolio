import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalhesPratoComponent } from './detalhes-prato.component';

describe('DetalhesPratoComponent', () => {
  let component: DetalhesPratoComponent;
  let fixture: ComponentFixture<DetalhesPratoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalhesPratoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetalhesPratoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
