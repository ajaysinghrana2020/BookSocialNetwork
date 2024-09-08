import { HttpInterceptorFn } from '@angular/common/http';
import {TokenService} from "../models/token/token.service";
import {inject} from "@angular/core";

export const httpIntrceptInterceptor: HttpInterceptorFn = (req, next) => {
  // if we want to inject the token to a function we can use inject dammmm
  const tokenService = inject(TokenService);
  // const token = localStorage.getItem('token');
  const token =tokenService.token;
  if (token) {
    const clonedReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(clonedReq);
  }
  return next(req);
};
