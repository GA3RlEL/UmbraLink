import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { HealthCheckService } from '../service/health-check.service';



export const websocketConnectionGuard: CanActivateFn = (route, state) => {
  const healthCheck = inject(HealthCheckService)

  return healthCheck.checkHealth();


};
