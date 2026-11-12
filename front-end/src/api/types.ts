/*
  The shape of the data the API will send to the app (Option B).
  The backend groups the workouts, so the app never maps exercise names.
  Field names are a proposal. Confirm them with the backend before wiring the real API.
*/

export type Unit = 'REPS' | 'SECONDS';

// One variant inside an exercise, e.g. Wide inside Push-ups.
export interface VariantTotal {
  variantId: string;
  name: string;
  total: number; // reps, or whole seconds for SECONDS exercises
}

// One exercise for one day, e.g. all Push-ups on Oct 14.
export interface ExerciseDay {
  exerciseId: string;
  name: string;
  unit: Unit;
  total: number; // sum of all variants, calculated by the backend
  variants: VariantTotal[];
}

// Everything done on one day.
export interface DayLog {
  date: string; // 'YYYY-MM-DD'
  exercises: ExerciseDay[];
}