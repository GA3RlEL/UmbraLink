export interface User {
  username: string;
  email: string;
  chatAsSender: Conversation[]
  chatAsRecipeint: Conversation[]
}

export interface Conversation {
  id: number;
  sender: User;
  recipient: User;
  messages: Message[]
}

export interface Message {
  id: number;
  content: string;
  messageState: "SENT" | "SEEN"
  messageType: "TEXT" | "AUDIO" | "PHOTO" | "VOICE"
  senderId: string;
  receiverId: string;
}