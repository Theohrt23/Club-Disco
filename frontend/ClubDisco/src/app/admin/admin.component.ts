import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  roles: any = [];
  user: any = JSON.parse(sessionStorage.getItem("auth-user") || "{}" );
  showAdminBoard = false;
  allUsers : any = [];

  constructor(
    private router : Router,
    private tokenStorageService: TokenStorageService,
    public userService: UserService
  ) { }

  ngOnInit(): void {
    this.roles = this.user.roles;
    this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
    if (!this.showAdminBoard){
      this.tokenStorageService.signOut();
      this.router.navigate(['login']);
    }
    this.loadAllUsers();
  }

  loadAllUsers(){
    this.userService.getAllUsers().subscribe((data: {}) => {
      this.allUsers = data;
    })
  }

  deleteUser(id: any){
    this.userService.deleteUser(id).subscribe();
    this.userService.deleteUser(id).subscribe();
    window.location.reload();
  }

  upgradeUser(id: any){
    this.userService.upgradeUser(id).subscribe();
    window.location.reload();
  }

}
