import {Component, Input} from '@angular/core';
import {BookResponse} from "../../../../services/models/book-response";

@Component({
  selector: 'app-book-card',
  standalone: true,
  templateUrl: './book-card.component.html',
  styleUrl: './book-card.component.scss'
})
export class BookCardComponent {
  private _book: BookResponse={};
  private _bookCover :string |undefined;

  get bookCover():string | undefined{
    if(this._bookCover){

    }
    return this._bookCover;
  }

  get book(): BookResponse{
    return this._book;
  }


  @Input()
  set book(value:BookResponse){
    this._book=value
  }
}
