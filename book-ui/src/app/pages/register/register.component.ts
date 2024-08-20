import { Component } from '@angular/core';
import {RegistrationRequest} from "../../services/models/registration-request";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {NgForOf, NgIf} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";
import {TokenService} from "../../services/models/token/token.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  standalone: true,
  imports: [
    MatCard,
    MatCardTitle,
    ReactiveFormsModule,
    MatFormField,
    MatCardContent,
    MatInput,
    MatButton,
    MatCardHeader,
    MatLabel,
    NgForOf,
    NgIf,
    RouterLink,
    FormsModule
  ],
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
registerRequest: RegistrationRequest={email:'',firstname:'',lastname:'',password:''};
errorMsg:Array<string>=[];

constructor(private router:Router,private authService:AuthenticationService,private tokenService: TokenService) {
  }


register(){
  this.errorMsg=[];
  this.authService.register({
    body:this.registerRequest
  }).subscribe({
    next:():void=>{
      this.router.navigate(['activate-account']);
    },
    error:(err):void =>{
      console.log(err);
      if (err.error.validationErrors){
        this.errorMsg=err.error.validationErrors;
      }
      else{
        this.errorMsg.push(err.error.errorCode);
      }
    }
  })
}







}
