import {Component, OnInit} from "@angular/core";
import {Book} from "../../dto/Book";
import {HomePageService} from "./home.service";
import {Observable} from "rxjs/Observable";

const count: number = 9;

@Component({
    selector: 'home-app',
    templateUrl: 'app/main/home/home.component.html'
})
export class HomeComponent implements OnInit {
    topBooks: Observable<Book[]>;

    constructor(private homePageService: HomePageService) {
    }

    getTopBooks(count: number) {
        this.topBooks = this.homePageService.getTopBooks(count);
    }

    ngOnInit() {
        this.getTopBooks(count);
    }
}