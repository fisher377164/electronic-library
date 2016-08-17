import {Routes, RouterModule} from "@angular/router";
import {PageNotFoundComponent} from "./not.found/page-not-found.component";
import {LoginComponent} from "./login/login.component";

const appRoutes: Routes = [
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: '**',
        component: PageNotFoundComponent
    }
];

export const routing = RouterModule.forRoot(appRoutes);
