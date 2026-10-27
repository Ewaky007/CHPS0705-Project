package com.kapture.jeu.engine.pieces;

import com.kapture.jeu.engine.Board;
import com.kapture.jeu.engine.Piece;
import com.kapture.jeu.engine.PieceColor;
import com.kapture.jeu.engine.PieceType;

/**
 * Implémentation du Cavalier.
 * Se déplace en "L" (2 cases dans une direction, 1 case dans la perpendiculaire).
 * Ignore totalement les collisions pendant son trajet.
 */
public class Cavalier extends Piece {

    public Cavalier(PieceColor couleur) {
        super(couleur, PieceType.CAVALIER);
    }

    @Override
    public boolean estDeplacementValide(int ligDep, int colDep, int ligArr, int colArr, Board plateau) {
        int diffLig = Math.abs(ligArr - ligDep);
        int diffCol = Math.abs(colArr - colDep);

        // Validation du motif en "L" (2x1 ou 1x2)
        return (diffLig == 2 && diffCol == 1) || (diffLig == 1 && diffCol == 2);
    }
}