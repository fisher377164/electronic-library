import {Routes, RouterModule} from "@angular/router";
import {HomeComponent} from "./home/home.component";
import {AboutComponent} from "./about/about.component";
import {MainComponent} from "./main.component";
import {CabinetComponent} from "./cabinet/cabinet.component";
import {FavoriteBooksComponent} from "./cabinet/favorite.books/favorite-books.component";
import {UserEditComponent} from "./cabinet/user.edit/user-edit.component";

const mainRoutes: Routes = [
    {
        path: '',
        component: MainComponent,
        children: [
            {
                path: '',
                component: HomeComponent
            },
            {
                path: 'about',
                component: AboutComponent
            }, {
                path: 'cabinet',
                component: CabinetComponent,
                children: [
                    {
                        path: '',
                        component: FavoriteBooksComponent
                    },
                    {
                        path: 'edit-user',
                        component: UserEditComponent
                    }
                ]
            }
        ]
    }
];

export const mainRouting = RouterModule.forChild(mainRoutes);
