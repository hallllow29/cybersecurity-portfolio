import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarEncomendasComponent } from './listar-encomendas.component';

describe('ListarEncomendasComponent', () => {
  let component: ListarEncomendasComponent;
  let fixture: ComponentFixture<ListarEncomendasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListarEncomendasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListarEncomendasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
