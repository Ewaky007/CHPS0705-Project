export type ViewKey = 'dashboard' | 'pilotage' | 'sessions' | 'signalements' | 'analyse' | 'synthese' | 'journal'
export type SessionStatus = 'En cours' | 'Terminée' | 'En attente'
export type ReportStatus = 'À qualifier' | 'En analyse' | 'Qualifié'

export interface Scenario {
  id: string
  name: string
  description: string
  category: string
  severity: 'Critique' | 'Majeure' | 'Modérée'
  active: boolean
  activations: number
}

export interface Session {
  id: string
  player: string
  game: string
  startedAt: string
  duration: string
  status: SessionStatus
  scenario: string
  events: number
  report: boolean
}

export interface Report {
  id: string
  sessionId: string
  player: string
  scenario: string
  title: string
  excerpt: string
  status: ReportStatus
  createdAt: string
  confidence: number
}

export interface Activity {
  time: string
  title: string
  detail: string
  tone: 'blue' | 'green' | 'orange' | 'purple'
}
