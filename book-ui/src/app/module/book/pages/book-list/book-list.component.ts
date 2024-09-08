import {Component, OnInit} from '@angular/core';
import {BookService} from "../../../../services/services/book.service";
import {Router} from "@angular/router";
import {PageResponseBookResponse} from "../../../../services/models/page-response-book-response";
import {NgForOf} from "@angular/common";
import {BookCardComponent} from "../../component/book-card/book-card.component";

@Component({
  selector: 'app-book-list',
  standalone: true,
  templateUrl: './book-list.component.html',
  imports: [
    BookCardComponent,
    NgForOf
  ],
  styleUrl: './book-list.component.scss'
})
export class BookListComponent implements OnInit{
  bookResponse:PageResponseBookResponse={};
  private page:number =0 ;
  private size:number= 5 ;

  constructor(private bookService:BookService,private router:Router) {
  }
  ngOnInit() :void{
    this.findAllBooks();
  }
  private findAllBooks(){
    this.bookService.findAllBooks({
      page:this.page,
      size:this.size
    }).subscribe({
      next:(books)=>{
        this.bookResponse=books;
      }
    })
  }

  books= [
    {
      title: 'The Great Gatsby',
      author: 'F. Scott Fitzgerald',
      description: 'A novel about the American dream.',
      coverUrl: 'assets/gatsby.jpg',
      price: 10.99
    },
    {
      title: '1984',
      author: 'George Orwell',
      description: 'A dystopian novel about totalitarianism.',
      coverUrl: 'assets/1984.jpg',
      price: 12.99
    },
    {
      title: 'The Great Gatsby',
      author: 'F. Scott Fitzgerald',
      description: 'A novel about the American dream.',
      coverUrl: 'assets/gatsby.jpg',
      price: 10.99
    },
    {
      title: '1984',
      author: 'George Orwell',
      description: 'A dystopian novel about totalitarianism.',
      coverUrl: 'assets/1984.jpg',
      price: 12.99
    },
    // Add more books as needed
  ];

}
