package com.kapture.jeu.engine.pieces;

import com.kapture.jeu.engine.Board;
import com.kapture.jeu.engine.Piece;
import com.kapture.jeu.engine.PieceColor;
import com.kapture.jeu.engine.PieceType;

/**
 * Implémentation des règles du Pion.
 * Le pion avance verticalement sans capturer, capture en diagonale, 
 * et dispose d'un mouvement double exclusif depuis sa ligne de départ.
 */
public class Pion extends Piece {

    public Pion(PieceColor couleur) {
        super(couleur, PieceType.PION);
    }

    @Override
    public boolean estDeplacementValide(int ligDep, int colDep, int ligArr, int colArr, Board plateau) {
        int direction = (this.couleur == PieceColor.BLANC) ? -1 : 1;
        int ligneDepart = (this.couleur == PieceColor.BLANC) ? 6 : 1;

        // Avancée standard (1 case, colonne identique, case d'arrivée vide)
        if (colDep == colArr && ligArr == ligDep + direction && plateau.getPiece(ligArr, colArr) == null) {
            return true;
        }

        // Avancée initiale (2 cases, colonne identique, trajectoire totalement vide)
        if (colDep == colArr && ligDep == ligneDepart && ligArr == ligDep + (2 * direction) 
            && plateau.getPiece(ligDep + direction, colDep) == null 
            && plateau.getPiece(ligArr, colArr) == null) {
            return true;
        }

        // Capture (diagonale adjacente, présence obligatoire d'une pièce ennemie)
        if (Math.abs(colArr - colDep) == 1 && ligArr == ligDep + direction 
            && plateau.getPiece(ligArr, colArr) != null) {
            return true;
        }

        return false;
    }
}