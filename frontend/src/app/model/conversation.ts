export interface Message {
  messageId: number;
  content: string;
  senderId: number | null;
  sentTime: Date;
  conversationId: number;
  state: State;
}

export interface Conversation {
  messages: Message[];
  conversationId: number;
  receiverId: number;
  receiverName: string;
}


export interface MessageToSend {
  messageType: "VIDEO" | "TEXT" | "AUDIO" | "PHOTO";
  senderId: number
  receiverId: number;
  content: string;
  conversationId: number;
  sentTime: Date;
}


export interface ReadMessage {
  messageId: number;
  conversationId: number;
  state: State;
}

export enum State {
  SENT = "SENT",
  SEEN = "SEEN"
}