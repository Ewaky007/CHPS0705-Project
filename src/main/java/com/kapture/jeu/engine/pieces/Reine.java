package com.kapture.jeu.engine.pieces;

import com.kapture.jeu.engine.Board;
import com.kapture.jeu.engine.Piece;
import com.kapture.jeu.engine.PieceColor;
import com.kapture.jeu.engine.PieceType;

/**
 * Implémentation de la Reine.
 * Combine les mouvements de la Tour et du Fou.
 */
public class Reine extends Piece {

    public Reine(PieceColor couleur) {
        super(couleur, PieceType.REINE);
    }

    @Override
    public boolean estDeplacementValide(int ligDep, int colDep, int ligArr, int colArr, Board plateau) {
        int diffLig = Math.abs(ligArr - ligDep);
        int diffCol = Math.abs(colArr - colDep);

        // Doit être une ligne droite ou une diagonale
        boolean estLigneDroite = (ligDep == ligArr || colDep == colArr);
        boolean estDiagonale = (diffLig == diffCol);

        if (!estLigneDroite && !estDiagonale) {
            return false;
        }

        // Trajectoire dégagée
        int pasLig = Integer.compare(ligArr, ligDep);
        int pasCol = Integer.compare(colArr, colDep);

        int ligCourante = ligDep + pasLig;
        int colCourante = colDep + pasCol;

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