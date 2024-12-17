import { Routes } from '@angular/router';
import { QuoteComponent } from './quote/quote.component';
import { LikeDislikeComponent } from './like-dislike/like-dislike.component';
import { TriviaComponent } from './trivia/trivia.component';

//export const routes: Routes = [];
export const routes: Routes = [
  { path: '', component: QuoteComponent },
  { path: 'like-dislike', component: LikeDislikeComponent },
  { path: 'trivia', component: TriviaComponent },
];
