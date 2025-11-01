import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdicionarPratoComponent } from './adicionar-prato.component';

describe('AdicionarPratoComponent', () => {
  let component: AdicionarPratoComponent;
  let fixture: ComponentFixture<AdicionarPratoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdicionarPratoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdicionarPratoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
