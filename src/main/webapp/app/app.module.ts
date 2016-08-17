import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {AppComponent} from "./app.component";
import {routing} from "./app.routing";
import {MainModule} from "./main/main.module";
import {HTTP_PROVIDERS} from "@angular/http";
import {AdminModule} from "./admin/admin.module";
import {LocalStorageService} from "angular2-localstorage/LocalStorageEmitter";
import {AuthHttpClient} from "./auth.http.client";

@NgModule({
    imports: [
        BrowserModule,
        routing,
        MainModule,
        AdminModule
    ],
    declarations: [
        AppComponent
    ],
    providers: [
        HTTP_PROVIDERS,
        LocalStorageService,
        AuthHttpClient
    ],
    bootstrap: [
        AppComponent
    ]
})
export class AppModule {
}