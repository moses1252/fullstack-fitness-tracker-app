/*
  Home screen: this week's reps, the mix by exercise, and time held.
  It only displays data. The math lives in lib/aggregate.ts.
*/
import { Card } from '../../components/Card';
import { StackedBar, type BarPart } from '../../components/StackedBar';
import type { DayLog } from '../../api/types';
import { formatShortDate, toIsoDate } from '../../lib/dates';
import { seriesColor } from '../../lib/colors';
import {
  type ExerciseTotal,
  logsInRange,
  totalsByExercise,
  weekRange,
} from '../../lib/aggregate';
import { formatDuration, formatNumber } from '../../lib/format';
import styles from './HomeScreen.module.css';

interface HomeScreenProps {
  logs: DayLog[];
  today: Date;
}

/** One bar segment per exercise. */
function exerciseParts(totals: ExerciseTotal[]): BarPart[] {
  return totals.map((total, i) => ({
    key: total.exerciseId,
    label: total.name,
    value: total.total,
    color: seriesColor(i),
  }));
}

/** One bar segment per variant, such as Regular or Wide inside Push-ups. */
function variantParts(total: ExerciseTotal): BarPart[] {
  return total.variants.map((variant, i) => ({
    key: variant.variantId,
    label: variant.name,
    value: variant.total,
    color: seriesColor(i),
  }));
}

export function HomeScreen({ logs, today }: HomeScreenProps) {
  const { from, to } = weekRange(today);
  const week = logsInRange(logs, from, to);
  const totals = totalsByExercise(week);

  const repTotals = totals.filter((t) => t.unit === 'REPS');
  const timeTotals = totals.filter((t) => t.unit === 'SECONDS');
  const repsThisWeek = repTotals.reduce((sum, t) => sum + t.total, 0);
  const activeDays = week.filter((day) => day.exercises.length > 0).length;

  return (
    <div className={styles.screen}>
      <header className={styles.header}>
        <p className={styles.kicker}>{formatShortDate(toIsoDate(today))}</p>
        <h1 className={styles.heading}>Your week</h1>
      </header>

      <div className={styles.grid}>
        <Card title="Reps">
          <div className={styles.bigNumber}>{formatNumber(repsThisWeek)}</div>
          <p className={styles.muted}>
            reps over {activeDays} active {activeDays === 1 ? 'day' : 'days'}
          </p>
          <StackedBar parts={exerciseParts(repTotals)} showLegend />
        </Card>

        <Card title="Reps by exercise">
          {repTotals.length === 0 && <p className={styles.muted}>No reps logged this week.</p>}
          {repTotals.map((total) => (
            <div key={total.exerciseId} className={styles.row}>
              <div className={styles.rowHead}>
                <span className={styles.rowName}>{total.name}</span>
                <span className={styles.rowValue}>{formatNumber(total.total)}</span>
              </div>
              <StackedBar parts={variantParts(total)} thin />
            </div>
          ))}
        </Card>

        {timeTotals.length > 0 && (
          <Card title="Time held">
            {timeTotals.map((total) => (
              <div key={total.exerciseId} className={styles.row}>
                <div className={styles.rowHead}>
                  <span className={styles.rowName}>{total.name}</span>
                  <span className={styles.rowValue}>{formatDuration(total.total)}</span>
                </div>
                <StackedBar parts={variantParts(total)} unit="SECONDS" thin />
              </div>
            ))}
          </Card>
        )}
      </div>
    </div>
  );
}