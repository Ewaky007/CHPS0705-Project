package com.kapture.jeu.engine.pieces;

import com.kapture.jeu.engine.Board;
import com.kapture.jeu.engine.Piece;
import com.kapture.jeu.engine.PieceColor;
import com.kapture.jeu.engine.PieceType;

/**
 * Implémentation du Roi.
 * Se déplace d'une seule case dans n'importe quelle direction.
 */
public class Roi extends Piece {

    public Roi(PieceColor couleur) {
        super(couleur, PieceType.ROI);
    }

    @Override
    public boolean estDeplacementValide(int ligDep, int colDep, int ligArr, int colArr, Board plateau) {
        int diffLig = Math.abs(ligArr - ligDep);
        int diffCol = Math.abs(colArr - colDep);

        return diffLig <= 1 && diffCol <= 1;
    }
}