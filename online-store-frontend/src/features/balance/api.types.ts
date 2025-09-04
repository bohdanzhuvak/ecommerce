import {z} from "zod";

export interface DepositRequest {
  amount: number;
  description?: string;
}

export const depositInputSchema = z.object({
  amount: z.coerce.number().min(0.01, 'Amount must be at least 0.01'),
  description: z.string().max(255).optional().or(z.literal('')),
})

export type DepositFormData = z.infer<typeof depositInputSchema>;
