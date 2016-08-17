import {Component} from "@angular/core";
import {Control, ControlGroup, FormBuilder, Validators, FORM_DIRECTIVES} from "@angular/common";
import {AuthHttpClient} from "../auth.http.client";
import {Api} from "../api";

@Component({
    selector: 'login-app',
    templateUrl: './app/login/login.component.html',
    providers: [FormBuilder],
    directives: [FORM_DIRECTIVES]
})
export class LoginComponent {

    loginForm: ControlGroup;
    username: Control;
    password: Control;
    submitAttempt: boolean = false;

    constructor(private authHttpClient: AuthHttpClient, private builder: FormBuilder) {

        // Synchronous validators are passed in as the second
        // argument to our Controls
        this.username = new Control('', Validators.required);
        // If we want to use more than one synchronous validators, we need to compose them
        this.password = new Control('', Validators.compose([Validators.required, Validators.minLength(8)]));

        this.loginForm = builder.group({
            username: this.username,
            password: this.password
        });
    }


    logIn(loginData: any) {
        this.submitAttempt = true;
        if (this.password.valid && this.username.valid) {

          this.authHttpClient.post(Api.LOG_IN_ENDPOINT, loginData)
                .map(Api.extractData)
                .catch(Api.handleError);
            console.log("test " + loginData.username + " " + loginData.password);
            // console.log(test);
        }
    }


}
