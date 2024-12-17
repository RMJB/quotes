import { Component, Input, Output, EventEmitter } from '@angular/core';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-quote-card',
  templateUrl: './quote-card.component.html',
  styleUrls: ['./quote-card.component.css'],
  imports: [
    MatButton
  ]
})
export class QuoteCardComponent {
  @Input() quote: any; // Accepts a quote object
  //@Output() like = new EventEmitter<void>();
  @Output() next = new EventEmitter<void>();

  // onLike() {
  //   this.like.emit();
  // }

  onNext() {
    this.next.emit();
  }
}
