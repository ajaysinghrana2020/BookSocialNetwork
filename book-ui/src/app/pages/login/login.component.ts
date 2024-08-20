import { Component } from '@angular/core';
import {AuthenticationRequest} from "../../services/models/authentication-request";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {MatButton} from "@angular/material/button";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";
import {AuthenticationResponse} from "../../services/models/authentication-response";
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {TokenService} from "../../services/models/token/token.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  standalone: true,
  imports: [
    MatLabel,
    MatFormField,
    MatInput,
    MatCard,
    MatCardContent,
    MatCardTitle,
    MatCardHeader,
    MatButton,
    FormsModule,
    NgIf,
    NgForOf
  ],
  styleUrl: './login.component.scss'
})

export class LoginComponent {
  constructor(private router:Router,private authService:AuthenticationService,private tokenService: TokenService) {
  }
  authRequest:AuthenticationRequest={email: '',password: ''};
  errorMsg:Array<string>=[];

  login(){
    this.errorMsg=[];
    this.authService.authenticate({
      body:this.authRequest
    }).subscribe({
      next:(res:AuthenticationResponse):void=>{
        // first we want to save the token
        this.tokenService.token=res.token as string;
        this.router.navigate(['books']);
      },
      error:(err):void=>{
        console.log(err);
        if (err.error.validationErrors){
          this.errorMsg=err.error.validationErrors;
        }
        else{
          this.errorMsg.push(err.error.errorCode);
        }
      }
    });
  }

  register(){
    this.router.navigate(['register'])
  }
}
