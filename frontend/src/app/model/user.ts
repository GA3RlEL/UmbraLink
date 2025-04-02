import { MessageType, State } from "./conversation";

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
  lastMessageType: MessageType;
  conversationId: number;
  otherUserId: number;
  isLastMessageSender: boolean;
  lastMessageTimestamp: string;
  lastMessageUpdateTimestamp: string;
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

export interface UpdateUsernameInterface {
  id: number;
  newUsername: string;
}

