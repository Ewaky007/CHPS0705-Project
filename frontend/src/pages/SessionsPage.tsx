import { useEffect, useState } from 'react'
import { adminApi } from '../api'
import type { Session } from '../types'
import { PageTitle } from '../components/PageTitle'

export default function SessionsPage() {
  const [sessions, setSessions] = useState<Session[]>([]); const [query, setQuery] = useState('')
  useEffect(() => { void adminApi.getSessions().then(setSessions) }, [])
  const filtered = sessions.filter((s) => `${s.id} ${s.player} ${s.scenario}`.toLowerCase().includes(query.toLowerCase()))
  return <><PageTitle eyebrow="OBSERVATION · SESSIONS" title="Sessions de jeu" description="Visualisez les sessions en cours ou passées, leurs événements et leur contexte expérimental." action={<div className="global-search inner-search"><input placeholder="Rechercher une session..." value={query} onChange={(e) => setQuery(e.target.value)} /></div>} /><section className="panel"><div className="filter-row"><span className="result-count">{filtered.length} sessions</span></div><div className="table-wrap"><table><thead><tr><th>Session</th><th>Joueur</th><th>Démarrage</th><th>Durée</th><th>Scénario associé</th><th>Événements</th><th>État</th></tr></thead><tbody>{filtered.map((s) => <tr key={s.id}><td><b>{s.id}</b><small>{s.game}</small></td><td>{s.player}</td><td>{s.startedAt}</td><td>{s.duration}</td><td>{s.scenario}</td><td>{s.events}</td><td><span className={`status-dot ${s.status === 'En cours' ? 'running' : ''}`} />{s.status}</td></tr>)}</tbody></table></div></section></>
}
