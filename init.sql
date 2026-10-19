CREATE DATABASE IF NOT EXISTS kapture;
USE kapture;

CREATE TABLE JOUEUR (
    id VARCHAR(20) PRIMARY KEY,
    pseudonyme VARCHAR(50) NOT NULL,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    email VARCHAR(100) NOT NULL,
    iban VARCHAR(34) NOT NULL,
    cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE JEU (
    id VARCHAR(20) PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE BUG (
    id VARCHAR(20) PRIMARY KEY,
    jeu_id VARCHAR(20) NOT NULL,
    type VARCHAR(20) NOT NULL,
    avantageux BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (jeu_id) REFERENCES JEU(id)
);

CREATE TABLE EXPERIENCE (
    id VARCHAR(20) PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    type_recompense VARCHAR(50),
    mode_jeu VARCHAR(20)
);

CREATE TABLE SESSION (
    id VARCHAR(20) PRIMARY KEY,
    joueur_id VARCHAR(20) NOT NULL,
    jeu_id VARCHAR(20) NOT NULL,
    experience_id VARCHAR(20),
    debut TIMESTAMP NOT NULL,
    fin TIMESTAMP,
    FOREIGN KEY (joueur_id) REFERENCES JOUEUR(id),
    FOREIGN KEY (jeu_id) REFERENCES JEU(id),
    FOREIGN KEY (experience_id) REFERENCES EXPERIENCE(id)
);

CREATE TABLE PARTIE (
    id VARCHAR(20) PRIMARY KEY,
    session_id VARCHAR(20) NOT NULL,
    resultat VARCHAR(10),
    debut TIMESTAMP NOT NULL,
    fin TIMESTAMP,
    FOREIGN KEY (session_id) REFERENCES SESSION(id)
);

CREATE TABLE EVENEMENT (
    id VARCHAR(20) PRIMARY KEY,
    partie_id VARCHAR(20) NOT NULL,
    bug_id VARCHAR(20),
    type VARCHAR(30) NOT NULL,
    horodatage TIMESTAMP NOT NULL,
    etat_jeu TEXT,
    FOREIGN KEY (partie_id) REFERENCES PARTIE(id),
    FOREIGN KEY (bug_id) REFERENCES BUG(id)
);

CREATE TABLE SIGNALEMENT (
    id VARCHAR(20) PRIMARY KEY,
    joueur_id VARCHAR(20) NOT NULL,
    session_id VARCHAR(20) NOT NULL,
    bug_id VARCHAR(20),
    description TEXT,
    signale_le TIMESTAMP NOT NULL,
    evenements_avant TEXT,
    FOREIGN KEY (joueur_id) REFERENCES JOUEUR(id),
    FOREIGN KEY (session_id) REFERENCES SESSION(id),
    FOREIGN KEY (bug_id) REFERENCES BUG(id)
);

CREATE TABLE RECOMPENSE (
    id VARCHAR(20) PRIMARY KEY,
    joueur_id VARCHAR(20) NOT NULL,
    experience_id VARCHAR(20),
    type VARCHAR(30),
    montant DECIMAL(10,2),
    attribuee_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (joueur_id) REFERENCES JOUEUR(id),
    FOREIGN KEY (experience_id) REFERENCES EXPERIENCE(id)
);

CREATE TABLE CLASSEMENT (
    id VARCHAR(20) PRIMARY KEY,
    joueur_id VARCHAR(20) NOT NULL,
    semaine DATE NOT NULL,
    score INT DEFAULT 0,
    jeux_joues INT DEFAULT 0,
    parties_jouees INT DEFAULT 0,
    FOREIGN KEY (joueur_id) REFERENCES JOUEUR(id)
);