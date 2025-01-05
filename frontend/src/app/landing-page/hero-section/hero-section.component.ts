import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-hero-section',
  imports: [],
  templateUrl: './hero-section.component.html',
  styleUrl: './hero-section.component.css'
})
export class HeroSectionComponent implements AfterViewInit {
  @ViewChild('title', { static: true }) title!: ElementRef<HTMLDivElement>;
  @ViewChild('phrase', { static: true }) phrase!: ElementRef<HTMLHeadingElement>
  @ViewChild('images', { static: true }) images!: ElementRef<HTMLDivElement>
  @ViewChild('buttons', { static: true }) buttons!: ElementRef<HTMLDivElement>
  order = [1, 4, 8, 3, 0, 5, 2, 7, 6]

  ngAfterViewInit(): void {
    const spans = this.title.nativeElement.querySelectorAll('span');
    const images = this.images.nativeElement.querySelectorAll("div");
    this.fade_in(spans, this.order, this.phrase, images, this.buttons)
  }

  fade_in(spans: NodeListOf<HTMLSpanElement>, order: number[], phrase: ElementRef<HTMLHeadingElement>, images: NodeListOf<HTMLDivElement>, buttons: ElementRef<HTMLDivElement>) {
    order.forEach((i, index) => {
      setTimeout(() => {
        spans.item(i).style.opacity = '1';
      }, index * 200);

    })
    setTimeout(() => {
      phrase.nativeElement.style.opacity = '1';
    }, order.length * 200);

    setTimeout(() => {
      buttons.nativeElement.style.opacity = '1';
    }, order.length * 200);

    setTimeout(() => {
      images.forEach((img, index) => {
        setTimeout(() => {
          img.style.opacity = '1';
        }, index * 500);
      })
    }, 500);


  }





}
