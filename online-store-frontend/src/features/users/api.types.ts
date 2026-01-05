import { z } from 'zod';

export interface DeleteUserRequest {
  userEmail: string;
}

export interface UpdateProfileRequest {
  email: string;
  firstName: string;
  lastName: string;
}

export const updateProfileInputSchema = z.object({
  firstName: z
    .string()
    .min(3, 'First name must be at least 3 characters')
    .max(50),
  lastName: z
    .string()
    .min(3, 'Last name must be at least 3 characters')
    .max(50),
});
