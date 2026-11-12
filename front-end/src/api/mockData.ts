/*
  Fake history in the shape the API will send (Option B).
  Push-ups are grouped by the backend, so the app shows the day's total
  and the split by variant without mapping any names.
*/
import type { DayLog, ExerciseDay, VariantTotal } from './types';

const sum = (variants: VariantTotal[]) => variants.reduce((s, v) => s + v.total, 0);

function isoDate(daysAgo: number): string {
  const d = new Date();
  d.setDate(d.getDate() - daysAgo);
  const y = d.getFullYear();
  const m = String(d.getMonth() + 1).padStart(2, '0');
  const day = String(d.getDate()).padStart(2, '0');
  return `${y}-${m}-${day}`;
}

function pushUps(day: number): ExerciseDay {
  const variants: VariantTotal[] = [
    { variantId: 'regular', name: 'Regular', total: 20 + day },
    { variantId: 'wide', name: 'Wide', total: 10 + day },
    { variantId: 'diamond', name: 'Diamond', total: 8 + day },
    { variantId: 'pike', name: 'Pike', total: 5 + day },
  ];
  return { exerciseId: 'pushups', name: 'Push-ups', unit: 'REPS', total: sum(variants), variants };
}

function plank(day: number): ExerciseDay {
  const variants: VariantTotal[] = [
    { variantId: 'regular', name: 'Regular', total: 60 + day * 2 },
    { variantId: 'side', name: 'Side', total: 30 + day },
  ];
  return { exerciseId: 'planks', name: 'Planks', unit: 'SECONDS', total: sum(variants), variants };
}

/** Builds `days` days of history ending today. Planks happen every other day. */
export function makeMockLogs(days = 14): DayLog[] {
  const logs: DayLog[] = [];
  for (let i = 0; i < days; i++) {
    const exercises: ExerciseDay[] = [pushUps(i)];
    if (i % 2 === 0) exercises.push(plank(i));
    logs.push({ date: isoDate(days - 1 - i), exercises });
  }
  return logs;
}