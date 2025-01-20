export interface Message {
  content: string;
  senderId: number;
  sentTime: Date;
}

export interface Conversation {
  messages: Message[];
}