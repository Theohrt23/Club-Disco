import { Component, OnInit } from '@angular/core';
import { AppConstants } from '../constant/app.constants';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../_services/user.service';
import { WidgetService } from '../_services/widget.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  currentUser: any;
  googleURL = AppConstants.GOOGLE_AUTH_URL;

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private route: ActivatedRoute, private userService: UserService, private router : Router, public widgetService : WidgetService) { }

  ngOnInit(): void {
    const token: string = this.route.snapshot.queryParamMap.get('token') || '{}';
    const error: any = this.route.snapshot.queryParamMap.get('error');
      if (this.tokenStorage.getToken() != '{}') {
        this.isLoggedIn = true;
        this.currentUser = this.tokenStorage.getUser();
        this.checkUserSpotify();
      }
      else if(token){
        this.tokenStorage.saveToken(token);
        this.userService.getCurrentUser().subscribe(
              data => {
                this.login(data);
              },
              err => {
                this.errorMessage = err.error.message;
              }
          );
      }
      else if(error){
        this.errorMessage = error;
        this.isLoginFailed = true;
      }
    }

    onSubmit(): void {
      this.authService.login(this.form).subscribe(
        data => {
          this.tokenStorage.saveToken(data.accessToken);
          this.login(data.user);
        },
        err => {
          this.errorMessage = err.error.message;
          this.isLoginFailed = true;
        }
      );
    }
  

    login(user:any): void {
      this.tokenStorage.saveUser(user);
      this.isLoginFailed = false;
      this.isLoggedIn = true;
      this.currentUser = this.tokenStorage.getUser();
      window.location.reload();
    }

    checkUserSpotify() {
      this.widgetService.haveSpotify(this.currentUser.id).subscribe((res) => {
        if (res === true) {
          this.landingPageSpotify();
        } else {
          this.router.navigate(['dashboard']);
        }
      })
    }

    landingPageSpotify(): void {
      fetch("http://localhost:8080/api/spotify/login")
      .then((response) => response.text())
      .then(response => {
        window.location.replace(response);
      })
  }



      

}
