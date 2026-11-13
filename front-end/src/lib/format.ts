/*
  Pure formatting helpers for numbers and durations.
  The exercise unit (REPS or SECONDS) decides how an amount is shown.
*/
import type { Unit } from '../api/types';

/** 1234 becomes '1,234'. */
export function formatNumber(value: number): string {
  return Math.round(value).toLocaleString('en-US');
}

/** 75 becomes '1:15'. 3725 becomes '1:02:05'. Used for timers and stopwatches. */
export function formatClock(totalSeconds: number): string {
  const s = Math.round(totalSeconds);
  const h = Math.floor(s / 3600);
  const m = Math.floor((s % 3600) / 60);
  const r = s % 60;
  const pad = (n: number) => String(n).padStart(2, '0');
  return h > 0 ? `${h}:${pad(m)}:${pad(r)}` : `${m}:${pad(r)}`;
}

/** 45 becomes '45 sec'. 90 becomes '1 min 30 sec'. Used for totals. */
export function formatDuration(totalSeconds: number): string {
  const s = Math.round(totalSeconds);
  if (s < 60) return `${s} sec`;
  const h = Math.floor(s / 3600);
  const m = Math.floor((s % 3600) / 60);
  const r = s % 60;
  if (h > 0) return m > 0 ? `${h} hr ${m} min` : `${h} hr`;
  return r > 0 ? `${m} min ${r} sec` : `${m} min`;
}

/** Reps show as a number. Seconds show as a duration. */
export function formatAmount(value: number, unit: Unit): string {
  return unit === 'SECONDS' ? formatDuration(value) : formatNumber(value);
}