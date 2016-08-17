import {Component} from "@angular/core";
import {HeaderComponent} from "./header/header.component";
import {FooterComponent} from "./footer/footer.component";

@Component({
    selector:'main',
    templateUrl: 'app/main/main.component.html',
    directives:[
        HeaderComponent,
        FooterComponent
    ]
})
export class MainComponent {
}