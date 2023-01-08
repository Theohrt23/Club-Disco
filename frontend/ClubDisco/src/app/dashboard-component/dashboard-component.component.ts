import { Component, OnInit } from '@angular/core';
import { ConfigService } from '../_services/config.service';
import { NasaService } from '../_services/nasa.service';
import { SpotifyService } from '../_services/spotify.service';
import { WeatherService } from '../_services/weather.service';
import { WidgetService } from '../_services/widget.service';
import { ZippopotamService } from '../_services/zippopotam.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard-component',
  templateUrl: './dashboard-component.component.html',
  styleUrls: ['./dashboard-component.component.scss']
})
export class DashboardComponentComponent implements OnInit {

  zForm: any = {};
  wForm: any = {};
  currentWeather : any = [];
  currentWeather7Days : any = [];
  currentZip : any = [];
  currentUserConfig : any = [];
  currentSpotifyNewReleases : any = [];
  currentSpotifyTopTracks : any = [];
  currentSpotifyFollowedArtists : any = [];
  currentSpotifyUser : any = [];
  currentPlaylistsSpotify : any = [];
  currentWidgets : any = [];
  nasaPicture : any = [];
  init : boolean = false;
  initW : boolean = false;
  isSubmited : boolean = true;
  user: any = JSON.parse(sessionStorage.getItem("auth-user") || "{}" );
  roles: any = [];
  showAdminBoard = false;
  isLoggedIn = false;
  isWidget = false;
  haveSpotify = false;
  haveZip = false;
  haveWeather = false;
  haveNasa = false;
  isWeather = true;
  isZip = true;
  isNasa = true;
  isSpotify = true;
  isPlaylist = true;
  

  constructor(
    private tokenStorageService: TokenStorageService,
    public configService: ConfigService,
    public widgetService: WidgetService,
    public nasaService: NasaService,
    public spotifyService: SpotifyService,
    public zippopotamService : ZippopotamService,
    public weatherService : WeatherService,
    private router : Router
  ) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (!this.isLoggedIn || this.tokenStorageService.getToken() === '{}') {
      this.router.navigate(['login']);
    }
    this.roles = this.user.roles;
    this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
    this.loadCurrentConfig();
    this.loadCurrentUserWidgets();
    this.checkUserSpotify();
    this.loadNasaPictures();
    setTimeout(function(){
      window.location.reload();
    },600000);
  }

  toggleWeather(){
    this.isWeather = !this.isWeather;
  }

  onZSubmit(id:any): void {
    this.loadZippoptam(id,this.zForm.region,this.zForm.zip_code);
    this.isSubmited = true;  
  }

  onWSubmit(id:any): void {
    this.loadWeather(id,this.wForm.city);
    this.isSubmited = true;  
  }

  nasaOk(){
    this.haveNasa = true;
  }

  weatherOk(){
    this.haveWeather = true;
  }

  zipOk(){
    this.haveZip = true;
  }

  addSpotify(){
    this.widgetService.addWidgetToUser(this.user.id,2).subscribe();
    this.landingPageSpotify();
    window.location.reload();
  }

  addWidget(widget_id: any){
    this.widgetService.addWidgetToUser(this.user.id,widget_id).subscribe();
    window.location.reload();
  }

  removeWidget(widget_id: any){
    this.widgetService.removeWidgetToUser(this.user.id,widget_id).subscribe();
    window.location.reload();
  }

  logout(){
    this.tokenStorageService.signOut();
    this.router.navigate(['login']);
  }

  toggleWidget(){
    this.isWidget = !this.isWidget;
  }
  toggleZip(){
    this.isZip = !this.isZip;
  }
  toggleNasa(){
    this.isNasa = !this.isNasa;
  }
  toggleSpotify(){
    this.isSpotify = !this.isSpotify;
  }
  toggleSpotifyPlaylist(){
    this.isPlaylist = !this.isPlaylist;
  }
  goToAdminView(){
    this.router.navigate(['admin']);
  }

  goToProfilView(){
    this.router.navigate(['profile']);
  }

  initZipopo(config_id:any, region:any, code_postal:any): void {
    if (region === null || code_postal === null){
      this.loadZippoptam(config_id,'fr',59000);
    }else {
      this.loadZippoptam(config_id,region,code_postal);
    }
    this.init = true;
  }

  initWeather(config_id:any, city:any): void {
    if (city === null){
      this.loadWeather(config_id,'Lille');
    }else {
      this.loadWeather(config_id,city);
    }
    this.initW = true;
  }

  loadCurrentUserWidgets() {
     return this.widgetService.getUserWidgets(this.user.id).subscribe((data: {}) => {
      this.currentWidgets = data;
    })
  }

  loadNasaPictures(){
      this.nasaService.getNasaPictures().subscribe((data: {}) => {
      this.nasaPicture = data;
    })
  }

  checkUserSpotify() {
    this.widgetService.haveSpotify(this.user.id).subscribe((res) => {
      if (res === true) {
        this.loadSpotifyPlaylists();
        this.loadSpotifyProfil();
        this.loadSpotifyFollowedArtists();
        this.loadSpotifyTopTracks();
        this.loadSpotifyNewReleases();
        this.haveSpotify = true;
      }
    })
  }

  oui(){}

  loadWeather(config_id:any, city:any){
    this.weatherService.getWeatherOfCity(city).subscribe((data: {}) => {
      this.currentWeather = data;
  })
  this.weatherService.getWeatherOfCityFor7Days(city).subscribe((data: {}) => {
    this.currentWeather7Days = data;
})
  this.configService.setWeatherConfig(config_id,city).subscribe((data: {}) => {
});
  }

  loadZippoptam(config_id:any, region:any, code_postal:any) {
    this.zippopotamService.getZippopotam(region,code_postal).subscribe((data: {}) => {
        this.currentZip = data;
    })
    this.configService.setZippopotamConfig(config_id,region,code_postal).subscribe((data: {}) => {
  });
  }

  loadCurrentConfig() {
    return this.configService.getUserConfig(this.user.id).subscribe((data: {}) => {
        this.currentUserConfig = data;
    })
  }

  loadSpotifyPlaylists() {
    return this.spotifyService.getSpotifyPlaylists().subscribe((data: {}) => {
          this.currentPlaylistsSpotify = data;
        })
  }

  loadSpotifyProfil() {
    return this.spotifyService.getSpotifyProfil().subscribe((data: {}) => {
          this.currentSpotifyUser = data;
        })
  }

  loadSpotifyFollowedArtists() {
    return this.spotifyService.getSpotifyFollowedArtists().subscribe((data: {}) => {
          this.currentSpotifyFollowedArtists = data;
        })
  }

  loadSpotifyTopTracks() {
    return this.spotifyService.getSpotifyFavTracks().subscribe((data: {}) => {
          this.currentSpotifyTopTracks = data;
          console.log(data);
        })
  }

  loadSpotifyNewReleases() {
    return this.spotifyService.getSpotifyNewReleases().subscribe((data: {}) => {
          this.currentSpotifyNewReleases = data;
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
