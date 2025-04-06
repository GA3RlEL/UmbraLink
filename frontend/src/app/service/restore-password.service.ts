import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BASEURL } from '../shared/helper/consts';
import { ErrorService } from './error.service';
import { catchError, Subject, throwError } from 'rxjs';
import { v4 as uuidv4 } from 'uuid'

@Injectable({
  providedIn: 'root'
})
export class RestorePasswordService {
  private http = inject(HttpClient)
  private errorService = inject(ErrorService)
  private isRestoreReq: Subject<boolean> = new Subject<boolean>
  private isReqSuccess: Subject<boolean> = new Subject<boolean>

  sendRestorePasswordRequest(email: string) {
    this.isRestoreReq.next(true)
    return this.http.post(BASEURL + "/token/reset/password", { email: email }).subscribe(
      {
        next: () => {
          this.isRestoreReq.next(false)
          this.isReqSuccess.next(true);
        },
        error: err => {
          this.errorService.addError({ id: uuidv4(), errorCode: err.error.errorCode, errorMessage: err.error.message })
          this.isRestoreReq.next(false)
          this.isReqSuccess.next(false);
        },
      }
    )
  }

  checkIsTokenValid(token: string) {
    return this.http.post(BASEURL + '/token/validate', { token: token }).pipe(catchError(err => {
      this.errorService.addError({
        id: uuidv4(), errorCode: err.error.errorCode, errorMessage
          : err.error.message
      })
      return throwError(() => err);
    }))

  }

  updatePassword(token: string, newPassword: string) {
    return this.http.post(BASEURL + '/user/restore/password', { token: token, newPassword: newPassword }).pipe(catchError((err) => {
      this.errorService.addError(
        { id: uuidv4(), errorCode: err.error.errorCode, errorMessage: err.error.message }
      )
      return throwError(() => err);
    }))
  }

  getRestoreReq(): Subject<boolean> {
    return this.isRestoreReq;
  }
  getIsReqSuccess(): Subject<boolean> {
    return this.isReqSuccess;
  }
}
