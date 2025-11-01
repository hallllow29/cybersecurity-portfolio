import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarPratoComponent } from './listar-prato.component';

describe('ListarPratoComponent', () => {
  let component: ListarPratoComponent;
  let fixture: ComponentFixture<ListarPratoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListarPratoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListarPratoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
