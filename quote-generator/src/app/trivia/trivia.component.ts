import { Component } from '@angular/core';
import { QuoteService } from '../quote.service';
import {NgForOf, NgIf} from '@angular/common';
import {MatButton} from '@angular/material/button';
import {catchError, map, Observable, of} from 'rxjs';

@Component({
  selector: 'app-trivia',
  templateUrl: './trivia.component.html',
  styleUrls: ['./trivia.component.css'],
  imports: [
    NgIf,
    NgForOf,
    MatButton
  ]
})
export class TriviaComponent {
  quote: any = { text: '', author: '' };
  choices: string[] = [];
  selectedChoice: string = '';
  correctAnswer: boolean | null = null;

  constructor(private quoteService: QuoteService) {
    this.loadTrivia();
  }

  loadTrivia() {
    this.quoteService.getRandomQuote().subscribe((data) => {
      this.quote = { text: data.text, author: data.author };

      // Create choices (3 random + 1 correct)
      this.generateChoices(data.author).subscribe((choices) => {
        this.choices = choices;
      });
    });
  }

  generateChoices(correctAuthor: string): Observable<string[]> {
    return this.quoteService.getAuthors().pipe(
      map((authors: string[]) => {
        // Filter out the correct author from the fetched authors
        const randomAuthors = authors.filter((author) => author !== correctAuthor);

        // Shuffle and include the correct author
        const shuffled = [...randomAuthors, correctAuthor].sort(() => Math.random() - 0.5);

        // Return only 4 choices
        return shuffled.slice(0, 4);
      }),
      catchError((error) => {
        console.error('Error fetching authors:', error);

        // Fallback to local authors if API call fails
        const fallbackAuthors = [
          'Albert Einstein',
          'Oscar Wilde',
          'Sigmund Freud', // Add more fallback authors if needed
        ];
        const randomAuthors = fallbackAuthors.filter((author) => author !== correctAuthor);
        const shuffled = [...randomAuthors, correctAuthor].sort(() => Math.random() - 0.5);
        return of(shuffled.slice(0, 4));
      })
    );
  }

  submitAnswer(choice: string) {
    this.selectedChoice = choice;
    this.correctAnswer = choice === this.quote.author;
  }

  onNext() {
    this.loadTrivia();
    this.correctAnswer = null;
  }
}
