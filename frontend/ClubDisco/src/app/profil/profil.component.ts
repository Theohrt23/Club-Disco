import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AuthService } from '../_services/auth.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-profil',
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.scss']
})
export class ProfilComponent implements OnInit {

  user: any = JSON.parse(sessionStorage.getItem("auth-user") || "{}" );
  editUserForm: FormGroup;
  form: any = {};
  errorMessage = '';
  passwordInvalid = false;
  passwordInvalidSize = false;

  constructor(
    public fb: FormBuilder,
    public userService : UserService,
    public authService : AuthService
  ) {
    this.editUserForm = this.fb.group({
      id: [this.user.id],
      displayName: [''],
      email: [''],
      password: [''],
      matchingPassword: ['']
    });
   }

  ngOnInit(): void {
  }

  editUser() {
    if (this.editUserForm.value.password != this.editUserForm.value.matchingPassword || this.editUserForm.value.password === '' || this.editUserForm.value.matchingPassword === '' ){
      this.passwordInvalid = true;
    }else if(this.editUserForm.value.password.length < 6){
      this.passwordInvalidSize = true;
    }
    else {
      this.passwordInvalid = false;
      this.passwordInvalidSize = false;
      this.userService.editUser(this.editUserForm.value,this.user.id).subscribe({
        next: (res: any) => {
          this.authService.showAlert('Data has been changed Relog into your account to see the changement!')
        },
        error: (err: any) => {
          this.authService.showAlert('Not correct!')
        }
      });
    }
  }

}
