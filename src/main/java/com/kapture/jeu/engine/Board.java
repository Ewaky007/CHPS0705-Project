package com.kapture.jeu.engine;

import com.kapture.jeu.engine.pieces.Cavalier;
import com.kapture.jeu.engine.pieces.Fou;
import com.kapture.jeu.engine.pieces.Pion;
import com.kapture.jeu.engine.pieces.Reine;
import com.kapture.jeu.engine.pieces.Roi;
import com.kapture.jeu.engine.pieces.Tour;

import java.util.ArrayList;
import java.util.List;

/**
 * Moteur central du jeu d'échecs.
 * Gère la matrice représentant l'échiquier, l'alternance des tours de jeu,
 * la mémorisation de l'historique des coups et la sérialisation de la partie 
 * en JSON pour la plateforme Kapture.
 */
public class Board {
    private Piece[][] grille;
    private PieceColor joueurActif = PieceColor.BLANC;
    private List<String> historiqueCoups = new ArrayList<>();

    public Board() {
        grille = new Piece[8][8];
        initialiserPlateau();
    }

    private void initialiserPlateau() {
        // --- NOIRS ---
        grille[0][0] = new Tour(PieceColor.NOIR);
        grille[0][1] = new Cavalier(PieceColor.NOIR);
        grille[0][2] = new Fou(PieceColor.NOIR);
        grille[0][3] = new Reine(PieceColor.NOIR);
        grille[0][4] = new Roi(PieceColor.NOIR);
        grille[0][5] = new Fou(PieceColor.NOIR);
        grille[0][6] = new Cavalier(PieceColor.NOIR);
        grille[0][7] = new Tour(PieceColor.NOIR);
        for (int i = 0; i < 8; i++) grille[1][i] = new Pion(PieceColor.NOIR);

        // --- BLANCS ---
        for (int i = 0; i < 8; i++) grille[6][i] = new Pion(PieceColor.BLANC);
        grille[7][0] = new Tour(PieceColor.BLANC);
        grille[7][1] = new Cavalier(PieceColor.BLANC);
        grille[7][2] = new Fou(PieceColor.BLANC);
        grille[7][3] = new Reine(PieceColor.BLANC);
        grille[7][4] = new Roi(PieceColor.BLANC);
        grille[7][5] = new Fou(PieceColor.BLANC);
        grille[7][6] = new Cavalier(PieceColor.BLANC);
        grille[7][7] = new Tour(PieceColor.BLANC);
    }

    public Piece getPiece(int ligne, int colonne) {
        return grille[ligne][colonne];
    }
    
    public PieceColor getJoueurActif() {
        return joueurActif;
    }

    /**
     * Tente d'exécuter un mouvement sur le plateau.
     * Effectue une simulation pour garantir que le joueur ne met pas son propre Roi en échec.
     * 
     * @return {@code true} si le déplacement est légal, {@code false} s'il est rejeté.
     */
    public boolean deplacerPiece(int ligDep, int colDep, int ligArr, int colArr) {
        Piece pieceDep = grille[ligDep][colDep];
        Piece pieceArr = grille[ligArr][colArr];

        if (pieceDep == null || pieceDep.getCouleur() != joueurActif) return false;
        if (pieceArr != null && pieceArr.getCouleur() == joueurActif) return false;

        if (!pieceDep.estDeplacementValide(ligDep, colDep, ligArr, colArr, this)) {
            return false;
        }

        // --- SIMULATION DU DÉPLACEMENT ---
        grille[ligArr][colArr] = pieceDep;
        grille[ligDep][colDep] = null;
        
        // --- VÉRIFICATION DE LA SÉCURITÉ DU ROI ---
        if (estEnEchec(joueurActif)) {
            // Coup illégal : le Roi est en danger. On annule la simulation.
            grille[ligDep][colDep] = pieceDep;
            grille[ligArr][colArr] = pieceArr;
            return false;
        }

        // Promotion du Pion en Reine 
        if (pieceDep.getType() == PieceType.PION) {
            if (pieceDep.getCouleur() == PieceColor.BLANC && ligArr == 0) {
                grille[ligArr][colArr] = new Reine(PieceColor.BLANC);
            } else if (pieceDep.getCouleur() == PieceColor.NOIR && ligArr == 7) {
                grille[ligArr][colArr] = new Reine(PieceColor.NOIR);
            }
        }
        
        // Enregistrement dans l'historique
        String log = String.format("%s %s : [%d,%d] -> [%d,%d]", 
        pieceDep.getCouleur().name(), pieceDep.getType().name(), ligDep, colDep, ligArr, colArr);
        historiqueCoups.add(log);
        
        joueurActif = (joueurActif == PieceColor.BLANC) ? PieceColor.NOIR : PieceColor.BLANC;
        return true;
    }

    /**
    * Scanne le plateau pour savoir si le Roi de la couleur donnée est menacé.
    */
    private boolean estEnEchec(PieceColor couleurRoi) {
        int ligRoi = -1, colRoi = -1;

        // Trouver les coordonnées du Roi
        for (int l = 0; l < 8; l++) {
            for (int c = 0; c < 8; c++) {
                Piece p = grille[l][c];
                if (p != null && p.getType() == PieceType.ROI && p.getCouleur() == couleurRoi) {
                    ligRoi = l;
                    colRoi = c;
                    break;
                }
            }
        }

        // Vérifier si une pièce adverse possède une trajectoire valide vers ce Roi
        for (int l = 0; l < 8; l++) {
            for (int c = 0; c < 8; c++) {
                Piece attaquant = grille[l][c];
                if (attaquant != null && attaquant.getCouleur() != couleurRoi) {
                    // Délégation : on demande à la pièce ennemie si elle peut taper le Roi
                    if (attaquant.estDeplacementValide(l, c, ligRoi, colRoi, this)) {
                        return true; 
                    }
                }
            }
        }
        return false;
    }


    /**
     * Vérifie si le joueur de la couleur donnée est en Échec et Mat.
     */
    public boolean estEchecEtMat(PieceColor couleur) {
        if (!estEnEchec(couleur)) {
            return false;
        }

        // On teste tous les mouvements de toutes les pièces de cette couleur
        for (int ligDep = 0; ligDep < 8; ligDep++) {
            for (int colDep = 0; colDep < 8; colDep++) {
                Piece piece = grille[ligDep][colDep];
                
                if (piece != null && piece.getCouleur() == couleur) {
                    for (int ligArr = 0; ligArr < 8; ligArr++) {
                        for (int colArr = 0; colArr < 8; colArr++) {
                            
                            if (piece.estDeplacementValide(ligDep, colDep, ligArr, colArr, this)) {
                                
                                Piece pieceArrTemp = grille[ligArr][colArr];
                                grille[ligArr][colArr] = piece;
                                grille[ligDep][colDep] = null;
                                
                                boolean toujoursEnEchec = estEnEchec(couleur);
                                
                                grille[ligDep][colDep] = piece;
                                grille[ligArr][colArr] = pieceArrTemp;
                                
                                if (!toujoursEnEchec) {
                                    return false; 
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // Si on a testé tous les coups et que le Roi est toujours menacé : c'est Mat.
        return true;
    }



     /**
     * Génère le JSON de l'état actuel du plateau, de l'historique des coups et du commentaire du joueur.
     */
    public String genererJSON(String desc) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        
        String descSecu = desc.replace("\"", "\\\"");
        json.append("  \"commentaire\": \"").append(descSecu).append("\",\n");
        
        json.append("  \"historique\": [\n");
        for (int i = 0; i < historiqueCoups.size(); i++) {
            json.append("    \"").append(historiqueCoups.get(i)).append("\"");
            if (i < historiqueCoups.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("  ],\n");
        
        json.append("  \"pieces\": [\n");
        boolean first = true;
        for (int lig = 0; lig < 8; lig++) {
            for (int col = 0; col < 8; col++) {
                Piece p = grille[lig][col];
                if (p != null) {
                    if (!first) {
                        json.append(",\n");
                    }
                    json.append(String.format(
                        "    {\"type\": \"%s\", \"couleur\": \"%s\", \"position\": [%d, %d]}",
                        p.getType().name(), p.getCouleur().name(), lig, col
                    ));
                    first = false;
                }
            }
        }
        json.append("\n  ]\n}");
        return json.toString();
    }
}