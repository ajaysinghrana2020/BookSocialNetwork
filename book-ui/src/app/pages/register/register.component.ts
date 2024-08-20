import { Component } from '@angular/core';
import {RegistrationRequest} from "../../services/models/registration-request";
import { ReactiveFormsModule} from "@angular/forms";
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {NgForOf, NgIf} from "@angular/common";
import {RouterLink} from "@angular/router";

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
    RouterLink
  ],
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
registerRequest: RegistrationRequest={email:'',firstname:'',lastname:'',password:''};
errorMsg:Array<string>=[];






}
