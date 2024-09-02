import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {MatInput,MatLabel} from "@angular/material/input";
import {CodeInputModule} from "angular-code-input";
import {AuthenticationService} from "../../services/services/authentication.service";
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-activate-account',
  standalone: true,
  templateUrl: './activate-account.component.html',
  imports: [
    MatInput,
    CodeInputModule,
    MatLabel,
    MatCardContent,
    MatCard,
    MatCardHeader,
    MatCardTitle,
    NgIf
  ],
  styleUrl: './activate-account.component.scss'
})
export class ActivateAccountComponent {

  message:string='';
  isOkay:boolean=true;
  submitted:boolean=false;

  constructor(private router:Router,private authServie:AuthenticationService) {

  }


  onCodeCompleated(token: string) {
    this.confirmAccount(token);

  }

  redirectToLogin() {
    this.router.navigate(['login']);
  }

  private confirmAccount(token: string) {
    this.authServie.confirm({
      token
    }).subscribe({next:()=>{
      this.message="Your Account has been SuccesFully Activated .\n Now You can Proceed to Login";
      this.submitted=true;
      },error:()=>{
      this.message="Token has been expired or Invalid";
      this.submitted=true;
      this.isOkay=false;
      }})

  }
}
