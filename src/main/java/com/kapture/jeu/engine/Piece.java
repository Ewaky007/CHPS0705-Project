package com.kapture.jeu.engine;

/**
 * Modèle de données représentant une pièce d'échecs individuelle sur le plateau.
 */
public class Piece {
    private PieceColor couleur;
    private PieceType type;

    public Piece(PieceColor couleur, PieceType type) {
        this.couleur = couleur;
        this.type = type;
    }

    public PieceColor getCouleur() { return couleur; }
    public PieceType getType() { return type; }
}