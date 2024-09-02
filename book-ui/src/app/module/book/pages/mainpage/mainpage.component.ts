import { Component } from '@angular/core';
import {RouterOutlet} from "@angular/router";
import {MenuComponent} from "../../component/menu/menu.component";

@Component({
  selector: 'app-mainpage',
  standalone: true,
  templateUrl: './mainpage.component.html',
  imports: [
    RouterOutlet,
    MenuComponent
  ],
  styleUrl: './mainpage.component.scss'
})
export class MainpageComponent {

}
