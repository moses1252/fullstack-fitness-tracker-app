/*
  A reusable panel. Every screen uses it, so panels look the same everywhere.
*/
import type { ReactNode } from 'react';
import styles from './Card.module.css';

interface CardProps {
  title?: string;
  children: ReactNode;
  className?: string;
}

export function Card({ title, children, className }: CardProps) {
  const classes = [styles.card, className].filter(Boolean).join(' ');

  return (
    <section className={classes} aria-label={title}>
      {title && <h2 className={styles.title}>{title}</h2>}
      {children}
    </section>
  );
}