import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarMenuComponent } from './criar-menu.component';

describe('CriarMenuComponent', () => {
  let component: CriarMenuComponent;
  let fixture: ComponentFixture<CriarMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CriarMenuComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CriarMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
