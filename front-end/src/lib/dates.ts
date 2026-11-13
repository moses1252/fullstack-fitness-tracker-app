/*
  Pure date helpers. No React here, so they are easy to test.
  Dates travel through the app as 'YYYY-MM-DD' strings. Convert them to
  Date objects only when you need to do math on them.
*/

const DAY_MS = 24 * 60 * 60 * 1000;

/** Format a Date as 'YYYY-MM-DD' using the local calendar day. */
export function toIsoDate(date: Date): string {
  const y = date.getFullYear();
  const m = String(date.getMonth() + 1).padStart(2, '0');
  const d = String(date.getDate()).padStart(2, '0');
  return `${y}-${m}-${d}`;
}

/** Parse 'YYYY-MM-DD' into a local Date at midnight. */
export function fromIsoDate(iso: string): Date {
  const [y, m, d] = iso.split('-').map(Number);
  return new Date(y, m - 1, d);
}

/** Return a new Date with `days` added. Never mutates the input. */
export function addDays(date: Date, days: number): Date {
  const next = new Date(date);
  next.setDate(next.getDate() + days);
  return next;
}

/** The Monday of the week containing `date`. Weeks start on Monday. */
export function startOfWeek(date: Date): Date {
  const mondayIndex = (date.getDay() + 6) % 7; // Monday = 0, Sunday = 6
  const midnight = new Date(date.getFullYear(), date.getMonth(), date.getDate());
  return addDays(midnight, -mondayIndex);
}

/** Whole calendar days from `a` to `b`. Works across daylight saving changes. */
export function daysBetween(a: Date, b: Date): number {
  const utcA = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
  const utcB = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());
  return Math.round((utcB - utcA) / DAY_MS);
}

/** Short label for an ISO date, e.g. 'Mon, Oct 12'. */
export function formatShortDate(iso: string): string {
  return fromIsoDate(iso).toLocaleDateString('en-US', {
    weekday: 'short',
    month: 'short',
    day: 'numeric',
  });
}