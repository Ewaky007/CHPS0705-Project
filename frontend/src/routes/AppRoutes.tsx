import { Navigate, Route, Routes } from 'react-router-dom'
import { AdminLayout } from '../components/AdminLayout'
import AnalysisPage from '../pages/AnalysisPage'
import DashboardPage from '../pages/DashboardPage'
import JournalPage from '../pages/JournalPage'
import PilotagePage from '../pages/PilotagePage'
import ReportsPage from '../pages/ReportsPage'
import SessionsPage from '../pages/SessionsPage'
import SynthesisPage from '../pages/SynthesisPage'

export default function AppRoutes() {
  return <Routes><Route path="/admin" element={<AdminLayout />}><Route index element={<DashboardPage />} /><Route path="pilotage" element={<PilotagePage />} /><Route path="sessions" element={<SessionsPage />} /><Route path="signalements" element={<ReportsPage />} /><Route path="analyse" element={<AnalysisPage />} /><Route path="synthese" element={<SynthesisPage />} /><Route path="journal" element={<JournalPage />} /></Route><Route path="*" element={<Navigate to="/admin" replace />} /></Routes>
}
