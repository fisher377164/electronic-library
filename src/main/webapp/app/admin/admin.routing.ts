import {Routes, RouterModule} from "@angular/router";
import {AdminComponent} from "./admin.component";
import {LoginComponent} from "../login/login.component";
import {StatisticComponent} from "./statistic/statistic.component";
import {UsersComponent} from "./users/users.component";
import {BooksComponent} from "./books/books.component";

const adminRouts: Routes = [
    {
        path: 'admin',
        component: AdminComponent,
        children: [
            {
                path: '',
                component: StatisticComponent
            },
            {
                path: 'users',
                component: UsersComponent
            },
            {
                path: 'books',
                component: BooksComponent
            }
        ]
    }
];

export const adminRouting = RouterModule.forChild(adminRouts);
