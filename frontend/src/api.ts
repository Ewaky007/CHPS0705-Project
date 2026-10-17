import { scenarios, sessions, reports } from './data'
import type { Report, Scenario, Session } from './types'

/**
 * Point d’entrée unique à remplacer par le client REST lorsque l’API sera disponible.
 * Les délais simulés permettent de garder les états de chargement réalistes côté UI.
 */
export const adminApi = {
  async getScenarios(): Promise<Scenario[]> {
    return Promise.resolve(scenarios)
  },
  async getSessions(): Promise<Session[]> {
    return Promise.resolve(sessions)
  },
  async getReports(): Promise<Report[]> {
    return Promise.resolve(reports)
  },
  async activateScenario(scenarioId: string, sessionId: string): Promise<void> {
    console.info('[API mock] activation', { scenarioId, sessionId, activatedAt: new Date().toISOString() })
  },
  async deactivateScenario(scenarioId: string, sessionId: string): Promise<void> {
    console.info('[API mock] désactivation', { scenarioId, sessionId, deactivatedAt: new Date().toISOString() })
  },
}
