package com.kapture.jeu.engine.pieces;

import com.kapture.jeu.engine.Board;
import com.kapture.jeu.engine.Piece;
import com.kapture.jeu.engine.PieceColor;
import com.kapture.jeu.engine.PieceType;

/**
 * Implémentation des règles de déplacement de la Tour.
 * Se déplace exclusivement de manière orthogonale (lignes et colonnes)
 * et ne peut pas traverser d'autres pièces.
 */
public class Tour extends Piece {

    public Tour(PieceColor couleur) {
        super(couleur, PieceType.TOUR);
    }

    @Override
    public boolean estDeplacementValide(int ligDep, int colDep, int ligArr, int colArr, Board plateau) {
        if (ligDep != ligArr && colDep != colArr) {
            return false;
        }
        
        int pasLig = Integer.compare(ligArr, ligDep);
        int pasCol = Integer.compare(colArr, colDep);

        int ligCourante = ligDep + pasLig;
        int colCourante = colDep + pasCol;

        // Scanner pour détecter les collisions avant la case d'arrivée
        while (ligCourante != ligArr || colCourante != colArr) {
            if (plateau.getPiece(ligCourante, colCourante) != null) {
                return false; 
            }
            ligCourante += pasLig;
            colCourante += pasCol;
        }
        
        return true;
    }
}