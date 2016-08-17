import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {adminRouting} from "./admin.routing";
import {AdminComponent} from "./admin.component";

@NgModule({
    imports: [
        BrowserModule,
        adminRouting
    ],
    declarations: [
        AdminComponent
    ]
})
export class AdminModule {
}