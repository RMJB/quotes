import { Component } from '@angular/core';
import {QuoteService} from '../quote.service';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-mostliked',
  imports: [
    NgIf,
    NgForOf
  ],
  templateUrl: './mostliked.component.html',
  styleUrl: './mostliked.component.css'
})
export class MostlikedComponent {
  likedQuotes: any[] = [];

  constructor(private quoteService: QuoteService) {
    this.showMostLikedQuotes();
  }

  showMostLikedQuotes(): void {
    this.quoteService.getMostLikedQuotes().subscribe((data) => {
      this.likedQuotes = data;
    });
  }
}
