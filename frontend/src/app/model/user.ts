export interface user {
  username: string;
  photo: string;
  email: string;
  conversations: conversation[]
  messages: message[]
}

export interface conversation {
  id: number;
  messages: message[]
  users: user[]
}

export interface message {
  id: number;
  message: string;
  sentAt: number;
  user: user;
}