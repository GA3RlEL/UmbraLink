export interface User {
  id: number;
  username: string;
  email: string;
  conversations: Conversation[];
}

export interface Conversation {
  otherUser: string;
  lastMessage: string;
  conversationId: number;
  otherUserId: number;
}

