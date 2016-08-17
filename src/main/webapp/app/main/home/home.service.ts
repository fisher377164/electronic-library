import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {Book} from "../../dto/Book";
import {Api} from "../../api";


@Injectable()
export class HomePageService {

    constructor(private http: Http) {
    }

    getTopBooks(booksCounter: number): Observable<Book[]> {

        // return BOOKS;
        //TODO url to config
        let url = Api.BEST_BOOKS_ENDPOINT + booksCounter;

        return this.http.get(url)
            .map(Api.extractData)
            .catch(Api.handleError);
    }
}