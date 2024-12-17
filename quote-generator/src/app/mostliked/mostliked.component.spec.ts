import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MostlikedComponent } from './mostliked.component';

describe('MostlikedComponent', () => {
  let component: MostlikedComponent;
  let fixture: ComponentFixture<MostlikedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MostlikedComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MostlikedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
