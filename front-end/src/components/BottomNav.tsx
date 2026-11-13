/*
  The navigation bar. It sits at the bottom on phones and becomes
  a left rail on wide screens (see BottomNav.module.css).
*/
import styles from './BottomNav.module.css';

export type TabId = 'home' | 'log' | 'progress';

interface BottomNavProps {
  active: TabId;
  onChange: (tab: TabId) => void;
}

const TABS: { id: TabId; label: string; icon: string }[] = [
  { id: 'home', label: 'Home', icon: '⌂' },
  { id: 'log', label: 'Log', icon: '+' },
  { id: 'progress', label: 'Progress', icon: '▮' },
];

export function BottomNav({ active, onChange }: BottomNavProps) {
  return (
    <nav className={styles.nav} aria-label="Main">
      <div className={styles.brand}>Tally</div>

      {TABS.map((tab) => (
        <button
          key={tab.id}
          type="button"
          className={styles.tab}
          aria-current={tab.id === active ? 'page' : undefined}
          onClick={() => onChange(tab.id)}
        >
          <span className={tab.id === 'log' ? styles.logIcon : styles.icon} aria-hidden="true">
            {tab.icon}
          </span>
          {tab.label}
        </button>
      ))}
    </nav>
  );
}