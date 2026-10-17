import type { SVGProps } from 'react'

type IconProps = SVGProps<SVGSVGElement> & { size?: number }
const Icon = ({ size = 20, children, ...props }: IconProps & { children: React.ReactNode }) => (
  <svg width={size} height={size} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true" {...props}>{children}</svg>
)

export const GridIcon = (p: IconProps) => <Icon {...p}><rect x="3" y="3" width="7" height="7" rx="1" /><rect x="14" y="3" width="7" height="7" rx="1" /><rect x="3" y="14" width="7" height="7" rx="1" /><rect x="14" y="14" width="7" height="7" rx="1" /></Icon>
export const BugIcon = (p: IconProps) => <Icon {...p}><path d="M8 8h8a4 4 0 0 1 4 4v2a6 6 0 0 1-12 0v-2a4 4 0 0 1 4-4Z" /><path d="M12 8V5M9 5h6M5 13H2M22 13h-3M6 8 4 6M18 8l2-2M7 19l-2 2M17 19l2 2M12 12v7" /></Icon>
export const UsersIcon = (p: IconProps) => <Icon {...p}><path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2" /><circle cx="9" cy="7" r="4" /><path d="M22 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75" /></Icon>
export const FlagIcon = (p: IconProps) => <Icon {...p}><path d="M5 21V4" /><path d="M5 4c6-3 8 3 14 0v10c-6 3-8-3-14 0" /></Icon>
export const ChartIcon = (p: IconProps) => <Icon {...p}><path d="M3 3v18h18" /><path d="m7 16 4-5 3 2 5-7" /></Icon>
export const BookIcon = (p: IconProps) => <Icon {...p}><path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20" /><path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2Z" /></Icon>
export const SettingsIcon = (p: IconProps) => <Icon {...p}><circle cx="12" cy="12" r="3" /><path d="M19.4 15a1.7 1.7 0 0 0 .34 1.88l.06.06-1.9 1.9-.06-.06a1.7 1.7 0 0 0-1.88-.34 1.7 1.7 0 0 0-1.03 1.56V20h-2.7v-.08a1.7 1.7 0 0 0-1.03-1.56 1.7 1.7 0 0 0-1.88.34l-.06.06-1.9-1.9.06-.06A1.7 1.7 0 0 0 7.75 15a1.7 1.7 0 0 0-1.56-1.03H6v-2.7h.08A1.7 1.7 0 0 0 7.64 10.2a1.7 1.7 0 0 0-.34-1.88l-.06-.06 1.9-1.9.06.06a1.7 1.7 0 0 0 1.88.34A1.7 1.7 0 0 0 12.11 5.2V5h2.7v.08a1.7 1.7 0 0 0 1.03 1.56 1.7 1.7 0 0 0 1.88-.34l.06-.06 1.9 1.9-.06.06a1.7 1.7 0 0 0-.34 1.88 1.7 1.7 0 0 0 1.56 1.03H21v2.7h-.08A1.7 1.7 0 0 0 19.4 15Z" /></Icon>
export const SearchIcon = (p: IconProps) => <Icon {...p}><circle cx="11" cy="11" r="7" /><path d="m20 20-4-4" /></Icon>
export const BellIcon = (p: IconProps) => <Icon {...p}><path d="M18 8a6 6 0 0 0-12 0c0 7-3 7-3 9h18c0-2-3-2-3-9M10 21h4" /></Icon>
export const ChevronIcon = (p: IconProps) => <Icon {...p}><path d="m9 18 6-6-6-6" /></Icon>
export const PlayIcon = (p: IconProps) => <Icon {...p}><path d="m8 5 11 7-11 7V5Z" /></Icon>
export const PauseIcon = (p: IconProps) => <Icon {...p}><path d="M7 5v14M17 5v14" /></Icon>
export const CloseIcon = (p: IconProps) => <Icon {...p}><path d="m6 6 12 12M18 6 6 18" /></Icon>
export const FilterIcon = (p: IconProps) => <Icon {...p}><path d="M4 6h16M7 12h10M10 18h4" /></Icon>
