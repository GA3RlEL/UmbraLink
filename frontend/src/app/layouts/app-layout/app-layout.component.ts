import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { SideBarComponent } from "../../main-app/side-bar/side-bar.component";
import { AppService } from '../../service/app.service';
import { WebsocketService } from '../../service/websocket.service';
import { first } from 'rxjs';
import { WebsocketErrorComponent } from "../../shared/websocket-error/websocket-error.component";
import { animate, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-app-layout',
  imports: [RouterModule, SideBarComponent, WebsocketErrorComponent],
  templateUrl: './app-layout.component.html',
  styleUrl: './app-layout.component.css',
  animations: [
    trigger("errorShow", [
      transition(':enter', [
        style({ transform: 'translateY(-200%)' }),
        animate('0.2s ease-in', style({ transform: 'translateY(0)' }))
      ]),
      transition(':leave', [
        style({ transform: 'translateY(0)' }),
        animate('0.2s ease-in', style({ transform: 'translateY(-200%)' }))
      ]),
    ])
  ]
})
export class AppLayoutComponent implements OnInit, OnDestroy {
  constructor(private appService: AppService, private websocket: WebsocketService, private router: Router, private activeRoute: ActivatedRoute) { }

  isMobile = false;

  get websocketStatus() {
    return this.websocket.getIsConnected();
  }


  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.isMobile = event.target.innerWidth <= 750
    this.navigateToCorrectDisplay(event.target.innerWidth <= 750)
  }

  navigateToCorrectDisplay(isMobile: boolean) {
    this.activeRoute.url.pipe(first()).subscribe(value => {
      let activePath = value.map(val => val.path).join("");

      if (isMobile && activePath !== 'app-mobile') {
        this.router.navigate(['app-mobile']);
      } else if (!isMobile && activePath !== 'app') {
        this.router.navigate(['app']);
      }
    });

  }

  checkSize() {
    this.isMobile = window.innerWidth <= 750
    this.navigateToCorrectDisplay(window.innerWidth <= 750)
  }

  ngOnInit(): void {
    this.checkSize();

    this.appService.getUserDetails()?.subscribe({
      next: (user) => {
        this.appService.setUser(user);
      },
      error: (err) => {
        console.error(err);
      }
    });

    if (localStorage.getItem('authToken')) {
      this.websocket.connect({ "Authorization": localStorage.getItem("authToken") });
    }

  }



  ngOnDestroy(): void {
    this.websocket.disconnect();
  }


}
