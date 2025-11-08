import { z } from 'zod';

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
