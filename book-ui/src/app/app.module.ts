import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import { LoginComponent } from './pages/login/login.component';
import {MatToolbar} from "@angular/material/toolbar";
import {MatFormField} from "@angular/material/form-field";
import {ReactiveFormsModule} from "@angular/forms";
import {MatInput} from "@angular/material/input";
import { RegisterComponent } from './pages/register/register.component';
import {MatCard, MatCardContent, MatCardTitle} from "@angular/material/card";
import {MatButton} from "@angular/material/button";

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MatToolbar,
    MatFormField,
    ReactiveFormsModule,
    MatInput,
    MatCardTitle,
    MatCard,
    MatCardContent,
    MatButton
  ],
  providers: [
    provideClientHydration(),
    provideAnimationsAsync(),
    HttpClient,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
