import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { adminApi } from '../api'
import { activities } from '../data'
import { BugIcon, ChartIcon, FlagIcon, PlayIcon, UsersIcon } from '../icons'
import type { Report, Scenario, Session } from '../types'
import { PageTitle } from '../components/PageTitle'

function Stat({ label, value, detail, icon: Icon, tone }: { label: string; value: string; detail: string; icon: typeof UsersIcon; tone: string }) {
  return <div className="stat-card"><div className={`stat-icon ${tone}`}><Icon size={20} /></div><div><span>{label}</span><strong>{value}</strong><small>{detail}</small></div></div>
}

export default function DashboardPage() {
  const [sessions, setSessions] = useState<Session[]>([]); const [reports, setReports] = useState<Report[]>([]); const [scenarios, setScenarios] = useState<Scenario[]>([])
  useEffect(() => { void Promise.all([adminApi.getSessions(), adminApi.getReports(), adminApi.getScenarios()]).then(([s, r, c]) => { setSessions(s); setReports(r); setScenarios(c) }) }, [])
  return <><PageTitle eyebrow="VUE D’ENSEMBLE" title="Bonjour Clara 👋" description="Voici l’état de votre expérimentation aujourd’hui." action={<Link className="button primary" to="/admin/pilotage"><PlayIcon size={16} /> Piloter un scénario</Link>} /><div className="stat-grid"><Stat label="Sessions actives" value={String(sessions.filter((s) => s.status === 'En cours').length).padStart(2, '0')} detail="+2 depuis hier" tone="blue" icon={UsersIcon} /><Stat label="Scénarios actifs" value={String(scenarios.filter((s) => s.active).length).padStart(2, '0')} detail="sur 4 scénarios" tone="purple" icon={BugIcon} /><Stat label="Signalements à traiter" value={String(reports.filter((r) => r.status !== 'Qualifié').length).padStart(2, '0')} detail="+1 depuis hier" tone="orange" icon={FlagIcon} /><Stat label="Sessions analysées" value="84%" detail="+8% cette semaine" tone="green" icon={ChartIcon} /></div><div className="dashboard-grid"><section className="panel chart-panel"><div className="panel-heading"><div><h2>Activité des sessions</h2><p>Sessions et signalements sur les 7 derniers jours</p></div></div><div className="fake-chart"><div className="chart-y"><span>40</span><span>30</span><span>20</span><span>10</span><span>0</span></div><div className="chart-area"><div className="grid-lines" /><svg viewBox="0 0 680 190" preserveAspectRatio="none" aria-label="Graphique de sessions"><path d="M0 150 C38 140 50 115 90 130 S145 163 185 105 S250 124 285 92 S340 130 380 75 S430 102 475 80 S535 95 570 52 S625 78 680 32" fill="none" stroke="#4b65e8" strokeWidth="3" /></svg></div></div></section><section className="panel activity-panel"><div className="panel-heading"><div><h2>Activité récente</h2><p>Dernières actions de l’expérimentation</p></div><Link className="text-button" to="/admin/journal">Tout voir</Link></div><div className="activity-list">{activities.map((activity) => <div className="activity-item" key={activity.time}><span className={`activity-dot ${activity.tone}`} /><div><b>{activity.title}</b><p>{activity.detail}</p></div><time>{activity.time}</time></div>)}</div></section></div></>
}
