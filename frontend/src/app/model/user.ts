export interface User {
  username: string;
  email: string;
  conversations: Conversation[];
}

export interface Conversation {
  otherUser: string;
  lastMessage: string;
  conversationId: number;
}

