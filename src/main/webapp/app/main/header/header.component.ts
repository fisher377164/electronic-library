import {Component} from "@angular/core";
import { SEMANTIC_COMPONENTS, SEMANTIC_DIRECTIVES } from "ng-semantic";

@Component({
    selector:'header-app',
    directives: [SEMANTIC_COMPONENTS, SEMANTIC_DIRECTIVES],
    templateUrl: 'app/main/header/header.component.html'
})
export class HeaderComponent {
}