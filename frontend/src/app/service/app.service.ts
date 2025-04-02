import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { UpdateUsernameInterface, User } from '../model/user';
import { Conversation } from '../model/conversation';
import { FindOther } from '../model/findOther';
import { SideBarConversation } from '../model/user';
import { Observable } from 'rxjs';
import { tokenResponse } from '../model/auth';
import { BASEURL } from '../shared/helper/consts';

@Injectable({
  providedIn: 'root'
})
export class AppService {
  private user = signal<User | null>(null);

  constructor(private http: HttpClient) {
  }
  getUserDetails() {
    const token = localStorage.getItem("authToken");
    if (token) {
      return this.http.get<User>(BASEURL + "/user/me", {
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
      return this.http.get<SideBarConversation>(BASEURL + `/conversation/${conversationId}/metadata`, {
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
      return this.http.get<Conversation>(BASEURL + `/conversation/${id}`,
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
      return this.http.get<FindOther[]>(BASEURL + "/user/findOther", {
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
      return this.http.post(BASEURL + "/conversation/findConversation", { user1: user, user2: user2 }, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
    }

    return null;

  }

  updateUsername(newUsername: string) {
    const token = localStorage.getItem('authToken');
    if (token) {
      return this.http.patch<UpdateUsernameInterface>(BASEURL + "/user/update/username", { newUsername: newUsername }, {
        headers: {
          "Authorization": `Bearer ${token}`
        }
      })
    }

    return null;
  }

  sendPhotoMessage(form: FormData) {
    const token = localStorage.getItem('authToken');
    if (token) {
      return this.http.post(BASEURL + "/messages/addPhotoMessage",
        form,
        {
          headers: {
            "Authorization": `Bearer ${token}`
          }
        })
    }
    return null;
  }

  updatePassword(oldPassword: string, newPassword: string) {
    const token = localStorage.getItem('authToken');
    if (token) {
      return this.http.patch<tokenResponse>(BASEURL + "/user/update/password", {
        oldPassword: oldPassword,
        newPassword: newPassword,
      }, {
        headers: {
          "Authorization": `Bearer ${token}`
        }
      })
    }
    return null;
  }

  saveAvatar(formData: FormData) {
    const token = localStorage.getItem('authToken');
    if (token) {
      return this.http.post(BASEURL + "/image/save", formData, {
        headers: {
          "Authorization": `Bearer ${token}`
        }
      })
    }
    return null;
  }
}
