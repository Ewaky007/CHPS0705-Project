# CHPS0705 Project

## Frontend administrateur Kapture

Ce dépôt contient actuellement le **frontend uniquement** du Proof of Concept
Kapture. Il s’agit d’une interface d’administration permettant de :

- piloter les scénarios de bugs expérimentaux ;
- cibler une session et activer ou désactiver un scénario ;
- suivre les sessions de jeu et leurs événements ;
- consulter les signalements des bêta-testeurs ;
- analyser les six comportements définis dans la proposition technique ;
- consulter une synthèse et le journal des actions administratives.

Le backend et l’API REST ne sont pas encore présents. Les données utilisées
actuellement sont donc des données de démonstration, isolées derrière une
couche API afin de faciliter le raccordement futur.

## Prérequis

- [Node.js](https://nodejs.org/) installé, de préférence une version LTS ;
- npm, installé automatiquement avec Node.js ;
- un terminal ouvert à la racine du projet.

Vérifier l’installation :

```bash
node --version
npm --version
```

## Installation et lancement

Toutes les commandes suivantes doivent être exécutées depuis la racine du
projet, sauf indication contraire.

### Première installation

Après avoir récupéré le projet pour la première fois, se placer dans le dossier
du frontend puis installer les dépendances :

```bash
cd frontend
npm install
```

Cette commande crée le dossier `node_modules` et installe les dépendances
déclarées dans `frontend/package.json`. Elle doit normalement être exécutée une
seule fois, sauf après une modification des dépendances ou une suppression du
dossier `node_modules`.

### Lancer le projet après installation

Lorsque les dépendances sont déjà installées, il suffit d’exécuter :

```bash
cd frontend
npm run dev
```

Vite affiche ensuite une adresse locale, généralement :

```text
http://localhost:5173
```

Ouvrir cette adresse dans un navigateur pour accéder à l’interface
administrateur.

Pour arrêter le serveur de développement, utiliser `Ctrl+C` dans le terminal.

### Vérifier la version de production

Pour compiler et vérifier le frontend :

```bash
cd frontend
npm run build
```

La commande vérifie TypeScript puis génère les fichiers de production dans
`frontend/dist/`.

Pour prévisualiser la version compilée :

```bash
cd frontend
npm run preview
```

## Architecture du frontend

```text
frontend/
├── src/
│   ├── api.ts                  # Couche d’accès aux données et futures requêtes REST
│   ├── data.ts                 # Données mockées de démonstration
│   ├── types.ts                # Types des objets utilisés par l’interface
│   ├── App.tsx                 # Initialisation du routeur
│   ├── main.tsx                # Point d’entrée React
│   ├── styles.css              # Styles globaux et responsive design
│   ├── components/
│   │   ├── AdminLayout.tsx     # Sidebar, header et layout partagé
│   │   └── PageTitle.tsx       # En-tête réutilisable des pages
│   ├── pages/
│   │   ├── DashboardPage.tsx   # Tableau de bord
│   │   ├── PilotagePage.tsx    # Pilotage des scénarios
│   │   ├── SessionsPage.tsx    # Suivi des sessions
│   │   ├── ReportsPage.tsx     # Signalements
│   │   ├── AnalysisPage.tsx    # Analyse comportementale
│   │   ├── SynthesisPage.tsx   # Synthèse
│   │   └── JournalPage.tsx     # Journal d’activité
│   └── routes/
│       └── AppRoutes.tsx        # Déclaration des routes React Router
├── package.json
├── package-lock.json
└── vite.config.ts
```

### Principe de séparation

Chaque fonctionnalité est isolée dans sa propre page. Le composant
`AdminLayout` contient uniquement les éléments communs à toutes les pages et
rend la page active avec React Router via `Outlet`.

Les pages ne sont donc pas reliées entre elles par un état local : elles sont
accessibles par de vraies URLs. Cela facilite l’ajout de nouvelles pages, la
gestion des droits administrateur et le branchement ultérieur de l’API.

## Routes administrateur

Chaque fonctionnalité administrateur possède sa propre page et sa propre URL :

| URL | Page |
| --- | --- |
| `/admin` | Tableau de bord |
| `/admin/pilotage` | Pilotage expérimental |
| `/admin/sessions` | Sessions |
| `/admin/signalements` | Signalements |
| `/admin/analyse` | Analyse comportementale |
| `/admin/synthese` | Synthèse |
| `/admin/journal` | Journal d’activité |

Toute URL inconnue redirige vers `/admin`.

## Préparation du branchement backend

Les pages utilisent actuellement [api.ts](frontend/src/api.ts) au lieu
d’accéder directement aux données mockées. Lorsque l’API REST sera disponible,
les fonctions de ce fichier pourront être remplacées par des appels HTTP
versionnés, par exemple :

```ts
async getSessions(): Promise<Session[]> {
  const response = await fetch('/api/v1/admin/sessions')
  if (!response.ok) {
    throw new Error('Impossible de récupérer les sessions')
  }
  return response.json()
}
```

Les pages et les routes n’auront pas besoin d’être réécrites pour cette
évolution.
