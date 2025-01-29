import { State } from "./conversation";

export interface User {
  id: number;
  username: string;
  email: string;
  status: Status;
  conversations: Conversation[];
}

export interface Conversation {
  otherUser: string;
  lastMessage: string;
  conversationId: number;
  otherUserId: number;
  isLastMessageSender: boolean;
  state: State;
  status: Status;
}

export enum Status {
  ONLINE = "ONLINE",
  OFFLINE = "OFFLINE"
}

export interface StatusInterface {
  id: number;
  status: Status
}

