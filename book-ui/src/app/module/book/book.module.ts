import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BookRoutingModule } from './book-routing.module';
import { BookCardComponent } from './component/book-card/book-card.component';

@NgModule({
  declarations: [

  ],
  exports: [

  ],
  imports: [
    CommonModule,
    BookRoutingModule
  ]
})
export class BookModule { }
