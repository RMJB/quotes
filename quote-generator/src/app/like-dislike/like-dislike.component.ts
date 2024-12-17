import {Component, EventEmitter, Input, Output} from '@angular/core';
import { QuoteService } from '../quote.service';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-like-dislike',
  templateUrl: './like-dislike.component.html',
  styleUrls: ['./like-dislike.component.css'],
  imports: [
    MatButton,
  ]
})
export class LikeDislikeComponent {
  @Input() quote: any = { text: '', author: '' , id:''};
  likedQuotes: any[] = [];
  //dislikedQuotes: any[] = [];
  @Output() next = new EventEmitter<void>();

  constructor(private quoteService: QuoteService) {
    this.fetchRandomQuote();
  }

  fetchRandomQuote() {
    console.log('fetching quote')
    this.quoteService.getRandomQuote().subscribe((data) => {
      this.quote = {
        author: data.author,
        text: data.text,
        id: data.id,
      };
    });
    console.log('fetched quote ' + this.quote.id)

  }

  likeQuote(): void {
    if (this.quote.id) {
      this.quoteService.likeQuote(this.quote.id).subscribe(() => {
        console.log('Quote liked!');
      });
    }
    this.fetchRandomQuote();
  }

  dislikeQuote(): void {
    if (this.quote.id) {
      this.quoteService.dislikeQuote(this.quote.id).subscribe(() => {
        console.log('Quote disliked!');
      });
    }
    this.fetchRandomQuote();
  }

  showMostLikedQuotes(): void {
    this.quoteService.getMostLikedQuotes().subscribe((data) => {
      this.likedQuotes = data;
    });
  }

  onNext() {
    this.next.emit();
    this.fetchRandomQuote();
  }
}
