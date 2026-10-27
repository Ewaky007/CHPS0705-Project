package com.kapture.jeu.engine;

/**
 * Classe abstraite représentant une entité sur l'échiquier.
 */
public abstract class Piece {
    protected PieceColor couleur;
    protected PieceType type;

    /**
     * Initialise les attributs d'une pièce.
     * @param couleur Le camp de la pièce (BLANC ou NOIR).
     * @param type L'identifiant de la pièce pour la sérialisation JSON.
     */
    public Piece(PieceColor couleur, PieceType type) {
        this.couleur = couleur;
        this.type = type;
    }

    public PieceColor getCouleur() { return couleur; }
    public PieceType getType() { return type; }

    /**
     * Détermine si le déplacement demandé respecte les règles de la pièce
     * et l'état actuel de l'échiquier.
     * 
     * @param ligDep Ligne de départ (0-7)
     * @param colDep Colonne de départ (0-7)
     * @param ligArr Ligne d'arrivée visée (0-7)
     * @param colArr Colonne d'arrivée visée (0-7)
     * @param plateau L'instance actuelle du plateau pour vérifier les collisions
     * @return {@code true} si le coup est légal, {@code false} sinon.
     */
    public abstract boolean estDeplacementValide(int ligDep, int colDep, int ligArr, int colArr, Board plateau);
}