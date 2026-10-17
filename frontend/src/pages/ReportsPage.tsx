import { useEffect, useState } from 'react'
import { adminApi } from '../api'
import { BugIcon, UsersIcon } from '../icons'
import type { Report } from '../types'
import { PageTitle } from '../components/PageTitle'

export default function ReportsPage() {
  const [reports, setReports] = useState<Report[]>([]); const [status, setStatus] = useState('Toutes')
  useEffect(() => { void adminApi.getReports().then(setReports) }, [])
  const filtered = reports.filter((r) => status === 'Toutes' || r.status === status)
  return <><PageTitle eyebrow="OBSERVATION · SIGNALEMENTS" title="Signalements reçus" description="Consultez le rapport du bêta-testeur et rapprochez-le des événements de sa session." /><div className="report-tabs">{['Toutes', 'À qualifier', 'En analyse', 'Qualifié'].map((item) => <button className={status === item ? 'selected' : ''} onClick={() => setStatus(item)} key={item}>{item}<span>{item === 'Toutes' ? reports.length : reports.filter((r) => r.status === item).length}</span></button>)}</div><div className="reports-grid">{filtered.map((report) => <article className="panel report-card" key={report.id}><div className="report-top"><span className="report-id">{report.id}</span><span className={`badge ${report.status === 'Qualifié' ? 'green' : report.status === 'En analyse' ? 'blue' : 'orange'}`}>{report.status}</span></div><h2>{report.title}</h2><p>{report.excerpt}</p><div className="report-meta"><span><UsersIcon size={14} /> {report.player}</span><span><BugIcon size={14} /> {report.scenario}</span></div><div className="report-footer"><span>{report.createdAt}</span><button className="row-link">Voir le contexte</button></div></article>)}</div></>
}
