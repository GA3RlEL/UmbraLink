import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { User } from '../model/user';
import { Conversation } from '../model/conversation';
import { FindOther } from '../model/findOther';
import { SideBarConversation } from '../model/user';
import { Observable } from 'rxjs';

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

  fetchNewConversation(conversationId: number): Observable<SideBarConversation> {
    const token = localStorage.getItem("authToken");
    if (token) {
      return this.http.get<SideBarConversation>(this.BASEURL + `/conversation/${conversationId}/metadata`, {
        headers: {
          "Authorization": `Bearer ${token}`
        }
      })
    }
    console.error("Auth token was not found");
    return new Observable<SideBarConversation>(observer => {
      observer.error("Auth token was not found");
    });
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
    }
    console.error("Token was not found");
    return null;

  }

  setUser(user: User) {
    this.user.set(user);
  }

  getUser() {
    return this.user;
  }

  findUsers(data: string) {
    const token = localStorage.getItem('authToken');
    if (token) {
      return this.http.get<FindOther[]>(this.BASEURL + "/user/findOther", {
        params: {
          'data': data
        },
        headers: {
          'Authorization': `Bearer ${token}`
        }
      })
    }
    console.error('Token was not found');
    return null;
  }


  getConversationId(user: number, user2: number) {
    const token = localStorage.getItem('authToken');
    if (token) {
      return this.http.post(this.BASEURL + "/conversation/findConversation", { user1: user, user2: user2 }, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
    }

    return null;

  }
}
