export interface User {
  id: number;
  username: string;
  email: string;
  role: string;
}

export interface DeleteUserRequest {
  userEmail: string;
}

export interface UpdateProfileRequest {
  email: string;
  username: string;
}
