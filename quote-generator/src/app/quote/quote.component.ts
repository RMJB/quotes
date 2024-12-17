import { Component, OnInit } from '@angular/core';
import { QuoteService } from '../quote.service';
import {QuoteCardComponent} from '../quote-card/quote-card.component';

@Component({
  selector: 'app-quote',
  templateUrl: './quote.component.html',
  styleUrls: ['./quote.component.css'],
  imports: [
    QuoteCardComponent
  ]
})
export class QuoteComponent implements OnInit {
  quote: any = { id: '', text: '', author: '' }; // Placeholder for the quote
  likedQuotes: any[] = []; // Placeholder for most liked quotes

  constructor(private quoteService: QuoteService) {}

  ngOnInit(): void {
    this.fetchRandomQuote();
  }

  fetchRandomQuote(): void {
    this.quoteService.getRandomQuote().subscribe((data) => {
      this.quote = data;
    });
  }


}
