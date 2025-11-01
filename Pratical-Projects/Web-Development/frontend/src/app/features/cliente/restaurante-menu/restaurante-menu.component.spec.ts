import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RestauranteMenuComponent } from './restaurante-menu.component';

describe('RestauranteMenuComponent', () => {
  let component: RestauranteMenuComponent;
  let fixture: ComponentFixture<RestauranteMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RestauranteMenuComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RestauranteMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
