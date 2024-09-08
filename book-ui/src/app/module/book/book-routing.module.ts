import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
// import {MenuComponent} from "./component/menu/menu.component";
import {MainpageComponent} from "./pages/mainpage/mainpage.component";
import {BookListComponent} from "./pages/book-list/book-list.component";

const routes: Routes = [
  {
    path: '',
    component: MainpageComponent,
    children: [
      {
        path: '',
        component: BookListComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookRoutingModule {
}
