export interface loginRequest {
  email: string;
  password: string;
}

export interface tokenResponse {
  token: string;
  tokenType: string;
}
