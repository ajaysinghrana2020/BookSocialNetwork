import {Component, OnInit} from '@angular/core';
import {DataRowOutlet} from "@angular/cdk/table";
import {RouterLink} from "@angular/router";
import {MatToolbar} from "@angular/material/toolbar";
import {MatFormField} from "@angular/material/form-field";
import {MatIcon} from "@angular/material/icon";
import { Router } from '@angular/router';
import {MatInput} from "@angular/material/input";
import {MatAnchor, MatButton, MatIconButton} from "@angular/material/button";
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";
import {MatSidenav, MatSidenavContainer, MatSidenavContent} from "@angular/material/sidenav";

@Component({
  selector: 'app-menu',
  standalone: true,
  templateUrl: './menu.component.html',
  imports: [
    DataRowOutlet,
    RouterLink,
    MatToolbar,
    MatFormField,
    MatIcon,
    MatInput,
    MatIconButton,
    MatAnchor,
    MatButton,
    MatMenu,
    MatMenuItem,
    MatMenuTrigger,
    MatSidenavContainer,
    MatSidenav,
    MatSidenavContent
  ],
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit{
  constructor(private router:Router) {
  }
  ngOnInit() {
   //this method is use for the giving colour to the header by selecting every .nav-link and
    //then getting attribute href and then adding active to it or else removing it
    // const linkColor = document.querySelectorAll('.nav-link');
    // linkColor.forEach(link => {
    //   if (window.location.href.endsWith(link.getAttribute('href') || '')) {
    //     link.classList.add('active');
    //   }
    //   link.addEventListener('click', () => {
    //     linkColor.forEach(l => l.classList.remove('active'));
    //     link.classList.add('active');
    //   });
    // });
  }

  logout() {
    // Remove the token from storage
    // this.tokenService.removeToken();

    // Navigate to the login or home page
    this.router.navigate(['/login']); // Adjust route as necessary

    // Optional: Clear other user-specific data if necessary
    console.log('User logged out');
    localStorage.clear();
  }

}
