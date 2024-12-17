import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class QuoteService {
  private apiUrl = 'http://localhost:8080/api/quotes'; // Adjust URL as needed

  constructor(private http: HttpClient) {}

  // Get a random quote
  getRandomQuote(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/random`);
  }

  // Get Authors
  getAuthors(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/authors`);
  }

  // Send a like for a quote
  likeQuote(quoteId: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/${quoteId}/like`, null);
  }

  // Send a dislike for a quote
  dislikeQuote(quoteId: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/${quoteId}/dislike`, null);
  }

  // Get the most liked quotes
  getMostLikedQuotes(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/most-liked`);
  }
}
