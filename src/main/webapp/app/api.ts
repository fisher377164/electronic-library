import {Response} from "@angular/http";
import {Observable} from "rxjs/Observable";

export class Api {
    public static LOG_IN_ENDPOINT: string = 'http://localhost:8080/api/login';
    public static BEST_BOOKS_ENDPOINT: string = 'http://localhost:8080/api/book/getBest/';

    public static extractData(res: Response) {
        // let body = res.json();
        // console.log(res.json());
        return res.json() || {};
    }

    public static handleError(error: any) {
        // In a real world app, we might use a remote logging infrastructure
        // We'd also dig deeper into the error to get a better message
        let errMsg = (error.message) ? error.message :
            error.status ? `${error.status} - ${error.statusText}` : 'Server error';
        console.error(errMsg); // log to console instead
        return Observable.throw(errMsg);
    }
}

