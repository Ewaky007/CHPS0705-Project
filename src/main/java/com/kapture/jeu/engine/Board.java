package com.kapture.jeu.engine;

import java.util.ArrayList;
import java.util.List;

/**
 * Moteur logique du jeu d'échecs.
 * Gère la matrice 8x8, l'état des pièces, les tours de jeu et les règles de déplacement.
 */
public class Board {

    private Piece[][] grille;
    private PieceColor joueurActif = PieceColor.BLANC;
    private List<String> historiqueCoups = new ArrayList<>(); 

    public Board() {
        this.grille = new Piece[8][8];
        initPlateau();
    }

    /**
     * Initialise le plateau avec la disposition classique des 32 pièces d'échecs.
     */
    private void initPlateau() {
        // Placement des pions
        for (int i = 0; i < 8; i++) {
            grille[1][i] = new Piece(PieceColor.NOIR, PieceType.PION);
            grille[6][i] = new Piece(PieceColor.BLANC, PieceType.PION);
        }

        // Placement des figures majeures
        grille[0][0] = new Piece(PieceColor.NOIR, PieceType.TOUR);
        grille[0][1] = new Piece(PieceColor.NOIR, PieceType.CAVALIER);
        grille[0][2] = new Piece(PieceColor.NOIR, PieceType.FOU);
        grille[0][3] = new Piece(PieceColor.NOIR, PieceType.REINE);
        grille[0][4] = new Piece(PieceColor.NOIR, PieceType.ROI);
        grille[0][5] = new Piece(PieceColor.NOIR, PieceType.FOU);
        grille[0][6] = new Piece(PieceColor.NOIR, PieceType.CAVALIER);
        grille[0][7] = new Piece(PieceColor.NOIR, PieceType.TOUR);

        grille[7][0] = new Piece(PieceColor.BLANC, PieceType.TOUR);
        grille[7][1] = new Piece(PieceColor.BLANC, PieceType.CAVALIER);
        grille[7][2] = new Piece(PieceColor.BLANC, PieceType.FOU);
        grille[7][3] = new Piece(PieceColor.BLANC, PieceType.REINE);
        grille[7][4] = new Piece(PieceColor.BLANC, PieceType.ROI);
        grille[7][5] = new Piece(PieceColor.BLANC, PieceType.FOU);
        grille[7][6] = new Piece(PieceColor.BLANC, PieceType.CAVALIER);
        grille[7][7] = new Piece(PieceColor.BLANC, PieceType.TOUR);
    }

    public Piece getPiece(int lig, int col) {
        return grille[lig][col];
    }

    public PieceColor getJoueurActif() {
        return joueurActif;
    }

    /**
     * Tente de déplacer une pièce d'une case de départ vers une case d'arrivée.
     * Applique les règles de base (tour du joueur, non-cannibalisme).
     *
     * @param ligDep Ligne de départ
     * @param colDep Colonne de départ
     * @param ligArr Ligne d'arrivée
     * @param colArr Colonne d'arrivée
     * @return true si le déplacement est valide et a été effectué, false sinon.
     */
    public boolean deplacerPiece(int ligDep, int colDep, int ligArr, int colArr) {
        Piece pieceDep = grille[ligDep][colDep];
        
        // Vérification : la case de départ contient une pièce du joueur actif
        if (pieceDep == null || pieceDep.getCouleur() != joueurActif) {
            return false;
        }

        Piece pieceArr = grille[ligArr][colArr];
        
        // Vérification : impossible de capturer une pièce de son propre camp
        if (pieceArr != null && pieceArr.getCouleur() == pieceDep.getCouleur()) {
            return false;
        }

        // ENREGISTREMENT DU COUP
        String log = String.format("%s %s : [%d,%d] -> [%d,%d]", 
                pieceDep.getCouleur().name(), pieceDep.getType().name(), ligDep, colDep, ligArr, colArr);
        historiqueCoups.add(log);

        // Application du déplacement dans la matrice
        grille[ligArr][colArr] = pieceDep;
        grille[ligDep][colDep] = null;
        
        // Passage au tour suivant
        joueurActif = (joueurActif == PieceColor.BLANC) ? PieceColor.NOIR : PieceColor.BLANC;
        return true;
    }

    /**
     * Sérialise l'état actuel du plateau en format JSON pour le SDK Kapture.
     *
     * @param desc Le commentaire du testeur signalant l'anomalie.
     * @return Une chaîne de caractères contenant le payload JSON formaté.
     */
    public String genererJSON(String desc) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        
        String descSecu = desc.replace("\"", "\\\"");
        json.append("  \"commentaire\": \"").append(descSecu).append("\",\n");
        
        //INJECTION DE L'HISTORIQUE 
        json.append("  \"historique\": [\n");
        for (int i = 0; i < historiqueCoups.size(); i++) {
            json.append("    \"").append(historiqueCoups.get(i)).append("\"");
            if (i < historiqueCoups.size() - 1) {
                json.append(","); // Virgule pour tous sauf le dernier
            }
            json.append("\n");
        }
        json.append("  ],\n");

        json.append("  \"pieces\": [\n");
        boolean premier = true;

        for (int lig = 0; lig < 8; lig++) {
            for (int col = 0; col < 8; col++) {
                Piece p = grille[lig][col];
                if (p != null) {
                    if (!premier) {
                        json.append(",\n");
                    }
                    json.append(String.format("    {\"lig\": %d, \"col\": %d, \"couleur\": \"%s\", \"type\": \"%s\"}",
                            lig, col, p.getCouleur().name(), p.getType().name()));
                    premier = false;
                }
            }
        }
        
        json.append("\n  ]\n}");
        return json.toString();
    }
}