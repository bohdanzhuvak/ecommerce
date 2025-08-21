export const ROLES = {
  ADMIN: 'ADMIN',
  USER: 'USER',
} as const;

export type Role = typeof ROLES[keyof typeof ROLES];

export interface User {
  id: number;
  username: string;
  email: string;
  role: Role;
  balance: number;
}
