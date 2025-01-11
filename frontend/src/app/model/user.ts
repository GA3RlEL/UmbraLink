export interface user {
  id: number;
  username: string;
  email: string;
  password: string;
  role: string;
  sentMessages: string[]
  receivedMessages: string[]
}