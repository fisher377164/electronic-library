import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {mainRouting} from "./main.routing";
import {MainComponent} from "./main.component";
import {HomePageService} from "./home/home.service";

@NgModule({
    imports: [
        BrowserModule,
        mainRouting
    ],
    declarations: [
        MainComponent
    ],
    providers: [HomePageService]
})
export class MainModule {
}