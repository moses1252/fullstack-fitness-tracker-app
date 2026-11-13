/*
  The app shell. It owns the data and decides which screen shows.
  Screens receive their data as props and never fetch it themselves.
*/
import { useMemo, useState } from 'react';
import { makeMockLogs } from './api/mockData';
import { BottomNav, type TabId } from './components/BottomNav';
import { HomeScreen } from './features/home/HomeScreen';
import styles from './App.module.css';

function App() {
  const [tab, setTab] = useState<TabId>('home');

  // Fake data for now. Later this comes from the API.
  const logs = useMemo(() => makeMockLogs(), []);
  const today = useMemo(() => new Date(), []);

  return (
    <>
      <BottomNav active={tab} onChange={setTab} />

      <main className={styles.main}>
        {tab === 'home' && <HomeScreen logs={logs} today={today} />}
        {tab === 'log' && <p className={styles.placeholder}>Log screen: next step</p>}
        {tab === 'progress' && <p className={styles.placeholder}>Progress screen: later</p>}
      </main>
    </>
  );
}

export default App;