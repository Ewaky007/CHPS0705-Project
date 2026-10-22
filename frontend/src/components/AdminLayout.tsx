import { NavLink, Outlet, useLocation } from 'react-router-dom'
import { BellIcon, BookIcon, BugIcon, ChartIcon, ChevronIcon, FlagIcon, GridIcon, SearchIcon, SettingsIcon, UsersIcon } from '../icons'

const links = [
  { to: '/admin', label: 'Tableau de bord', icon: GridIcon, end: true },
  { to: '/admin/pilotage', label: 'Pilotage expérimental', icon: BugIcon },
  { to: '/admin/sessions', label: 'Sessions', icon: UsersIcon },
  { to: '/admin/signalements', label: 'Signalements', icon: FlagIcon, count: 3 },
  { to: '/admin/analyse', label: 'Analyse comportementale', icon: ChartIcon },
  { to: '/admin/synthese', label: 'Synthèse', icon: BookIcon },
]

export function AdminLayout() {
  const location = useLocation()
  const current = links.find((link) => link.end ? location.pathname === link.to : location.pathname.startsWith(link.to))
  const title = current?.label ?? 'Journal d’activité'
  return <div className="app-shell">
    <aside className="sidebar">
      <div className="brand"><div className="brand-mark">K</div><div><strong>kapture</strong><span>ADMINISTRATION</span></div></div>
      <div className="workspace-switch"><span className="workspace-dot" /><span><small>ESPACE DE TRAVAIL</small><b>Proof of Concept</b></span><ChevronIcon size={16} /></div>
      <nav className="main-nav" aria-label="Navigation administrateur">
        <p className="nav-heading">PILOTAGE</p>
        {links.slice(0, 2).map(({ to, label, icon: Icon, end }) => <NavLink className="nav-item" to={to} end={end} key={to}><Icon size={19} /><span>{label}</span>{to.endsWith('pilotage') && <i className="nav-dot" />}</NavLink>)}
        <p className="nav-heading">OBSERVATION</p>
        {links.slice(2).map(({ to, label, icon: Icon, count }) => <NavLink className="nav-item" to={to} key={to}><Icon size={19} /><span>{label}</span>{count && <em>{count}</em>}</NavLink>)}
      </nav>
      <div className="sidebar-bottom"><NavLink className="nav-item" to="/admin/journal"><BookIcon size={19} /><span>Journal d’activité</span></NavLink><button className="nav-item"><SettingsIcon size={19} /><span>Paramètres</span></button><div className="user-card"><div className="avatar">JJ</div><span><b>Jessica Jaunaux</b><small>Administratrice</small></span><ChevronIcon size={15} /></div></div>
    </aside>
    <main className="main-content">
      <header className="topbar"><div className="breadcrumbs"><span>Administration</span><ChevronIcon size={14} /><b>{title}</b></div><div className="top-actions"><div className="global-search"><SearchIcon size={17} /><input placeholder="Rechercher..." aria-label="Rechercher" /></div><button className="icon-button notification" aria-label="Notifications"><BellIcon size={19} /><i /></button><div className="top-avatar">JJ</div></div></header>
      <div className="page-content"><Outlet /></div>
    </main>
  </div>
}
