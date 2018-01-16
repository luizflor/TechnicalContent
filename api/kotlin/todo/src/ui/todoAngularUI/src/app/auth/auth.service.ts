import { Injectable } from '@angular/core';
import * as jwtDecode from 'jwt-decode';
import {Token} from '../model/Token';

export class AuthService {
  get token(): string {
    return this._token;
  }
  private _token: string;

  setToken(token: Token) {

    this._token = token.token;
    const tokenParse = jwtDecode(token.token);
    const userName = tokenParse['sub'];
    const scope = tokenParse['scope'];
    const expirationSeconds = (new Date(tokenParse['exp'] * 1000).getTime() - new Date().getTime()) / 1000;
    console.log('[AuthService]', expirationSeconds, userName, scope);


  }
  logout(): void {
    this._token = '';
  }
  isAuthenticated(): boolean {
    return this._token
      ? this.isTokenExpired(this._token)
      : false;
  }
  private getTokenExpirationDate(token: string): Date {
    let decoded: any;
    decoded = jwtDecode(token);

    if (!decoded.hasOwnProperty('exp')) {
      return null;
    }

    const date = new Date(0); // The 0 here is the key, which sets the date to the epoch
    date.setUTCSeconds(decoded.exp);

    return date;
  }

  private isTokenExpired(token: string, offsetSeconds?: number): boolean {
    const date = this.getTokenExpirationDate(token);
    offsetSeconds = offsetSeconds || 0;

    if (date == null) {
      return false;
    }

    // Token expired?
    return (date.valueOf() > (new Date().valueOf() + (offsetSeconds * 1000)));
  }
}
