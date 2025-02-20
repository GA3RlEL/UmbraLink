import { State } from "./conversation";

export interface User {
  id: number;
  username: string;
  email: string;
  status: Status;
  imageUrl: string;
  conversations: SideBarConversation[];
}

export interface SideBarConversation {
  otherUser: string;
  lastMessage: string;
  conversationId: number;
  otherUserId: number;
  isLastMessageSender: boolean;
  lastMessageTimestamp: string;
  state: State;
  status: Status;
  imageUrl: string;
}

export enum Status {
  ONLINE = "ONLINE",
  OFFLINE = "OFFLINE"
}

export interface StatusInterface {
  id: number;
  status: Status
}

export interface UpdatePhotoInteface {
  userId: number;
  imageUrl: string;
}

