
import {Component} from "@angular/core";
import {CabinetNavComponent} from "./cabinet.nav/cabinet-nav.component";

@Component({
    selector:'cabinet-app',
    templateUrl: 'app/main/cabinet/cabinet.component.html',
    directives:[
        CabinetNavComponent
    ]
})

export class CabinetComponent {
}