import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RestaurantesMapaComponent } from './restaurantes-mapa.component';

describe('RestaurantesMapaComponent', () => {
  let component: RestaurantesMapaComponent;
  let fixture: ComponentFixture<RestaurantesMapaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RestaurantesMapaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RestaurantesMapaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
