package com.kapture.jeu.engine.pieces;

import com.kapture.jeu.engine.Board;
import com.kapture.jeu.engine.Piece;
import com.kapture.jeu.engine.PieceColor;
import com.kapture.jeu.engine.PieceType;

/**
 * Implémentation du Fou.
 * Se déplace exclusivement en diagonale et ne peut pas traverser d'autres pièces.
 */
public class Fou extends Piece {

    public Fou(PieceColor couleur) {
        super(couleur, PieceType.FOU);
    }

    @Override
    public boolean estDeplacementValide(int ligDep, int colDep, int ligArr, int colArr, Board plateau) {
        int diffLig = Math.abs(ligArr - ligDep);
        int diffCol = Math.abs(colArr - colDep);

        // Mouvement strictement diagonal (écart ligne == écart colonne)
        if (diffLig != diffCol) {
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