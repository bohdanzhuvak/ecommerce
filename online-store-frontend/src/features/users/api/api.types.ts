import {BaseEntity} from "@/shared/types/api.ts";

export interface User extends BaseEntity{
  name: string;
  email: string;
  balance: string;
  roles: string[];
  updatedAt: string;
}

export type DeleteUserRequest = {
  userEmail: string;
};
