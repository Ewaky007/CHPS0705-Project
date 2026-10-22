import type { Activity, Report, Scenario, Session } from './types'

export const scenarios: Scenario[] = [
  { id: 'SCN-001', name: 'Bug 1', description: 'Le joueur traverse un obstacle non rendu dans la scène.', category: 'Physique', severity: 'Majeure', active: true, activations: 18 },
  { id: 'SCN-002', name: 'Bug 2', description: 'Une récompense apparaît deux fois après une reconnexion.', category: 'Économie', severity: 'Critique', active: false, activations: 11 },
  { id: 'SCN-003', name: 'Bug 3', description: 'La texture d’un élément est remplacée par un aplat neutre.', category: 'Affichage', severity: 'Modérée', active: false, activations: 9 },
  { id: 'SCN-004', name: 'Bug 4', description: 'Le son d’action est joué avec un décalage volontaire.', category: 'Audio', severity: 'Modérée', active: false, activations: 7 },
]

export const sessions: Session[] = [
  { id: 'SES-2408', player: 'Joueur 048', game: 'Kapture Arena', startedAt: 'Aujourd’hui, 14:32', duration: '18 min', status: 'En cours', scenario: 'Bug 1', events: 42, report: false },
  { id: 'SES-2407', player: 'Joueur 031', game: 'Kapture Arena', startedAt: 'Aujourd’hui, 14:08', duration: '26 min', status: 'Terminée', scenario: 'Bug 2', events: 87, report: true },
  { id: 'SES-2406', player: 'Joueur 019', game: 'Kapture Arena', startedAt: 'Aujourd’hui, 13:51', duration: '11 min', status: 'Terminée', scenario: 'Bug 3', events: 31, report: true },
  { id: 'SES-2405', player: 'Joueur 052', game: 'Kapture Arena', startedAt: 'Aujourd’hui, 13:20', duration: '34 min', status: 'Terminée', scenario: 'Aucun scénario', events: 104, report: false },
  { id: 'SES-2404', player: 'Joueur 007', game: 'Kapture Arena', startedAt: 'Aujourd’hui, 12:48', duration: '21 min', status: 'En attente', scenario: 'Aucun scénario', events: 0, report: false },
]

export const reports: Report[] = [
  { id: 'SIG-078', sessionId: 'SES-2407', player: 'Joueur 031', scenario: 'Bug 2', title: 'Récompense reçue deux fois', excerpt: 'Après la reconnexion, le coffre a attribué deux fois la même récompense.', status: 'À qualifier', createdAt: 'Il y a 24 min', confidence: 91 },
  { id: 'SIG-077', sessionId: 'SES-2406', player: 'Joueur 019', scenario: 'Bug 3', title: 'Un mur devient complètement blanc', excerpt: 'Le problème est apparu dans la zone du marché pendant quelques secondes.', status: 'En analyse', createdAt: 'Il y a 49 min', confidence: 76 },
  { id: 'SIG-076', sessionId: 'SES-2402', player: 'Joueur 044', scenario: 'Bug 1', title: 'Mon personnage reste bloqué', excerpt: 'Impossible de franchir le passage alors qu’il semble vide.', status: 'Qualifié', createdAt: 'Hier, 18:14', confidence: 96 },
]

export const activities: Activity[] = [
  { time: '14:32:08', title: 'Scénario activé', detail: 'Bug 1 · SES-2408', tone: 'blue' },
  { time: '14:29:41', title: 'Signalement reçu', detail: 'SIG-078 · Récompense reçue deux fois', tone: 'orange' },
  { time: '14:25:16', title: 'Session terminée', detail: 'SES-2407 · Joueur 031', tone: 'green' },
  { time: '14:18:03', title: 'Scénario désactivé', detail: 'Bug 3 · SES-2406', tone: 'purple' },
]
