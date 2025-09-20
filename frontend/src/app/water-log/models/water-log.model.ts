export interface WaterLog {
  id: number;
  amountOunces: number;
  loggedAt?: string;
  logDate?: string;
}

export interface DailyGoal {
  id: number;
  goalOunces: number;
  goalDate: string;
  createdAt?: string;
}

export interface DailySummary {
  goal: DailyGoal;
  totalOunces: number;
  logs: WaterLog[];
  progressPercentage: number;
}
