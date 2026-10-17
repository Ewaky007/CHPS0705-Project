# Kapture - Partie Jeu coté beta testeur

Il agit comme l'environnement du joueur (Testeur) et permet de signaler des comportements anormaux (bugs) en générant un  JSON. Ce JSON est destiné à être traité et affiché par l'interface d'administration.

## Technologies utilisées
* **Langage :** Java 21
* **Interface Graphique :** JavaFX
* **Gestionnaire de dépendances :** Maven

## Prérequis
Pour compiler et exécuter ce projet, vous devez avoir installé sur votre machine :
* [JDK 21](https://adoptium.net/) (ou supérieur)
* [Maven](https://maven.apache.org/)

## Installation et Lancement

1. Clonez ce dépôt sur votre machine locale.
2. Ouvrez un terminal à la racine du projet (où se trouve le fichier `pom.xml`).
3. Lancez la commande suivante pour compiler et démarrer l'application :

   ```bash
   mvn clean javafx:run
