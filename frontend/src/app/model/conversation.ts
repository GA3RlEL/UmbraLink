export interface Message {
  content: string;
  senderId: number | null;
  sentTime: Date;
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