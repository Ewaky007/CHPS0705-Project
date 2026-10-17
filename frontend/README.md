# Frontend administrateur Kapture

Interface React + TypeScript + Vite dédiée au parcours administrateur du POC.

## Démarrer

```bash
npm install
npm run dev
```

Pour générer la version de production :

```bash
npm run build
```

## Périmètre couvert

- tableau de bord des sessions, scénarios et signalements ;
- sélection d’un scénario expérimental et d’une session cible ;
- activation/désactivation manuelle avec confirmation et journalisation horodatée ;
- suivi des sessions et de leurs événements ;
- consultation et filtrage des signalements ;
- analyse déterministe selon les six comportements définis dans la proposition ;
- synthèse par scénario et journal d’activité ;
- affichage responsive pour les écrans plus étroits.

Les données actuelles sont des données de démonstration. Le fichier `src/api.ts` centralise les appels utilisés par l’interface afin de remplacer les mocks par les endpoints REST versionnés sans modifier les écrans.
