import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersCategoriaComponent } from './users-categoria.component';

describe('UsersCategoriaComponent', () => {
  let component: UsersCategoriaComponent;
  let fixture: ComponentFixture<UsersCategoriaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UsersCategoriaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsersCategoriaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
