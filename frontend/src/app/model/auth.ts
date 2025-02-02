export interface loginRequest {
  email: string;
  password: string;
}

export interface tokenResponse {
  token: string;
  tokenType: string;
}

export interface registerRequest {
  email: string;
  password: string;
  username: string;
}
