/*
  Pure functions that add up workout logs. No React here, so they are easy to test.
  The Home and Progress screens both use these.
*/
import type { DayLog, ExerciseDay, Unit, VariantTotal } from '../api/types';
import { addDays, startOfWeek, toIsoDate } from './dates';

/** One exercise's total across many days, with its variants merged. */
export interface ExerciseTotal {
  exerciseId: string;
  name: string;
  unit: Unit;
  total: number;
  variants: VariantTotal[];
}

/** The Monday-to-Sunday dates for the week containing `date`. */
export function weekRange(date: Date): { from: string; to: string } {
  const monday = startOfWeek(date);
  return {
    from: toIsoDate(monday),
    to: toIsoDate(addDays(monday, 6)),
  };
}

/** Keep only the days between `from` and `to`, inclusive. */
export function logsInRange(logs: DayLog[], from: string, to: string): DayLog[] {
  // 'YYYY-MM-DD' strings sort correctly, so we can compare them directly.
  return logs.filter((day) => day.date >= from && day.date <= to);
}

/** Add up every day into one total per exercise, merging variants across days. */
export function totalsByExercise(logs: DayLog[]): ExerciseTotal[] {
  const byExercise = new Map<string, ExerciseTotal>();

  for (const day of logs) {
    for (const exercise of day.exercises) {
      const existing = byExercise.get(exercise.exerciseId);
      byExercise.set(exercise.exerciseId, addExercise(existing, exercise));
    }
  }

  return [...byExercise.values()];
}

/** Return a new total with `incoming` added in. Never changes the inputs. */
function addExercise(existing: ExerciseTotal | undefined, incoming: ExerciseDay): ExerciseTotal {
  const base: ExerciseTotal = existing ?? {
    exerciseId: incoming.exerciseId,
    name: incoming.name,
    unit: incoming.unit,
    total: 0,
    variants: [],
  };

  const variants = [...base.variants];
  for (const variant of incoming.variants) {
    const index = variants.findIndex((v) => v.variantId === variant.variantId);
    if (index === -1) {
      variants.push({ ...variant });
    } else {
      variants[index] = { ...variants[index], total: variants[index].total + variant.total };
    }
  }

  return { ...base, total: base.total + incoming.total, variants };
}