import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";
import {LocalStorage} from "angular2-localstorage/WebStorage";

const AUTHORIZATION: string = 'Authorization';

@Injectable()
export class AuthHttpClient {

    @LocalStorage()
    public authToken: string = "";

    @LocalStorage()
    public isAuthenticated: boolean = false;

    constructor(private http: Http) {
    }

    createAuthorizationHeader(headers: Headers) {
        headers.append(AUTHORIZATION, this.authToken);
    }

    setToken(token: string) {
        this.authToken = token;
    }

    setAuthentication(authentication: boolean) {
        this.isAuthenticated = authentication;
    }

    get(url: string) {
        let headers = new Headers();
        this.createAuthorizationHeader(headers);
        return this.http.get(url, {
            headers: headers
        });
    }

    post(url: string, data: any) {
        let headers = new Headers();
        this.createAuthorizationHeader(headers);
        return this.http.post(url, data, {
            headers: headers
        });
    }
}