import { Injectable } from '@angular/core';
import { MONTHS } from '../shared/helper/constat';

@Injectable({
  providedIn: 'root'
})
export class DateService {

  constructor() { }

  showCorrectDate(date: string) {
    const currentTime = new Date();
    const databaseDate = new Date(date);
    const timeDiff = currentTime.getTime() - databaseDate.getTime();

    const seconds = Math.floor(timeDiff / 1000);
    const minutes = Math.floor(seconds / 60);
    const hours = Math.floor(minutes / 60);
    const days = Math.floor(hours / 24);


    if (days >= 1) {
      return `| ${databaseDate.getDate()} ${MONTHS[databaseDate.getMonth()]}`;
    }

    if (hours >= 1) {
      return `| ${hours} hours ago`;
    }

    if (minutes >= 1) {
      return `| ${minutes} minutes ago`;
    }

    return `| just sent`;
  }
}
