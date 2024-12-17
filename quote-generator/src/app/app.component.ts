import { Component } from '@angular/core';
import {MatMenu, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';
import {MatIcon} from '@angular/material/icon';
import {MatToolbar} from '@angular/material/toolbar';
import {QuoteComponent} from './quote/quote.component';
import {LikeDislikeComponent} from './like-dislike/like-dislike.component';
import {TriviaComponent} from './trivia/trivia.component';
import {MatIconButton} from '@angular/material/button';
import {NgIf} from '@angular/common';
import {MostlikedComponent} from './mostliked/mostliked.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  imports: [
    MatMenu,
    MatIcon,
    MatToolbar,
    MatMenuTrigger,
    QuoteComponent,
    LikeDislikeComponent,
    TriviaComponent,
    MatIconButton,
    MatMenuItem,
    NgIf,
    MostlikedComponent
  ]
})
export class AppComponent {
  currentMode: string = 'basic'; // Default mode
  title: string = 'quote-generator';

  setMode(mode: string) {
    this.currentMode = mode;
  }
}
