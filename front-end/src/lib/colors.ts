/*
  The series colors from the design, in order. Charts and legends pick
  colors by position, so each exercise keeps the same color on a screen.
*/
const SERIES_COLORS = [
  'var(--c1)',
  'var(--c2)',
  'var(--c3)',
  'var(--c4)',
  'var(--c5)',
  'var(--c6)',
] as const;

/** The color for the nth item. Wraps around after six. */
export function seriesColor(index: number): string {
  return SERIES_COLORS[index % SERIES_COLORS.length];
}