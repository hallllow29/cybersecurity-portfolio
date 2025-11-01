import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuDetalhesComponent } from './menu-detalhes.component';

describe('MenuDetalhesComponent', () => {
  let component: MenuDetalhesComponent;
  let fixture: ComponentFixture<MenuDetalhesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MenuDetalhesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MenuDetalhesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
