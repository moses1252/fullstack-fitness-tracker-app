/*
  The composition bar from the design. It shows how a total splits into
  parts, such as reps per variant. Each segment's width matches its value.
*/
import type { Unit } from '../api/types';
import { formatAmount } from '../lib/format';
import styles from './StackedBar.module.css';

export interface BarPart {
  key: string;
  label: string;
  value: number;
  color: string; // a CSS color, usually var(--c1) through var(--c6)
}

interface StackedBarProps {
  parts: BarPart[];
  unit?: Unit;
  thin?: boolean;
  showLegend?: boolean;
}

export function StackedBar({ parts, unit = 'REPS', thin = false, showLegend = false }: StackedBarProps) {
  const total = parts.reduce((sum, part) => sum + part.value, 0);
  const visible = parts.filter((part) => part.value > 0);

  // Screen readers get a plain sentence, such as "Regular 30, Wide 20".
  const description = visible.length
    ? visible.map((part) => `${part.label} ${formatAmount(part.value, unit)}`).join(', ')
    : 'Nothing yet';

  const barClasses = [styles.bar, thin && styles.thin].filter(Boolean).join(' ');

  return (
    <div className={styles.wrapper}>
      <div className={barClasses} role="img" aria-label={description}>
        {total === 0 ? (
          <div className={styles.empty} />
        ) : (
          visible.map((part) => (
            <div
              key={part.key}
              className={styles.segment}
              style={{ flexGrow: part.value, background: part.color }}
            />
          ))
        )}
      </div>

      {showLegend && total > 0 && (
        <ul className={styles.legend}>
          {visible.map((part) => (
            <li key={part.key}>
              <span className={styles.dot} style={{ background: part.color }} />
              {part.label} <b>{formatAmount(part.value, unit)}</b>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}