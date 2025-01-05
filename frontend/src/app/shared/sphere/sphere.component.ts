import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';

@Component({
  selector: 'app-sphere',
  imports: [],
  templateUrl: './sphere.component.html',
  styleUrl: './sphere.component.css'
})
export class SphereComponent implements AfterViewInit {
  @ViewChild('canvas') canvas!: ElementRef<HTMLCanvasElement>
  canvasEl!: HTMLCanvasElement;
  context!: CanvasRenderingContext2D
  private currentAngle = 0;

  ngAfterViewInit(): void {
    const canvasEl = this.canvas.nativeElement;
    if (canvasEl) {
      this.canvasEl = canvasEl;
      const context = canvasEl.getContext("2d");
      if (context) {
        this.context = context;
      }
    }

    if (this.context) {
      this.drawCircle()
    }
  }

  drawCircle() {
    const centerX = this.canvasEl.width / 2;
    const centerY = this.canvasEl.height / 2;
    const radius = 350;
    const endAngle = 2 * Math.PI;
    const speed = 0.05;

    this.context.shadowColor = 'rgba(255, 255, 255, 0.5)';
    this.context.shadowBlur = 30;

    this.context.beginPath();
    this.context.arc(centerX, centerY, radius, this.currentAngle - 0.1, this.currentAngle);
    this.context.strokeStyle = "white";
    this.context.lineWidth = 3;
    this.context.stroke();

    if (this.currentAngle < endAngle) {
      this.currentAngle += speed;
      requestAnimationFrame(() => this.drawCircle());
    }
  }


}
