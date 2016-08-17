import {Component} from "@angular/core";
import {AdminNavbarComponent} from "./navbar/navbar.component";

@Component({
    selector:'admin-app',
    templateUrl: 'app/admin/admin.component.html',
    directives:[
        AdminNavbarComponent
    ]
})
export class AdminComponent {
}