import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { User } from '../model/user';
import { Conversation, Message, MessageToSend } from '../model/conversation';

@Injectable({
  providedIn: 'root'
})
export class AppService {
  private BASEURL: string = "http://localhost:8080/api/v1"
  private user = signal<User | null>(null);

  constructor(private http: HttpClient) {
  }
  getUserDetails() {
    const token = localStorage.getItem("authToken");
    if (token) {
      return this.http.get<User>(this.BASEURL + "/user/me", {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      })
    } else {
      console.error("Token was not found");
      return null;
    }
  }

  getConversationMessages(id: string) {
    const token = localStorage.getItem("authToken");
    if (token) {
      return this.http.get<Conversation>(this.BASEURL + `/conversation/${id}`,
        {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        }
      )
    } else {
      console.error("Token was not found");
      return null;
    }
  }

  setUser(user: User) {
    this.user.set(user);
  }

  getUser() {
    return this.user;
  }
}
