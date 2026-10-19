package com.kapture.jeu.controller;

import com.kapture.jeu.engine.Board;
import com.kapture.jeu.engine.Piece;
import com.kapture.jeu.engine.PieceColor;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Contrôleur JavaFX faisant le pont entre l'interface utilisateur (Vues) et le moteur logique (Board).
 */
public class GameController {

    @FXML private GridPane grillePlateau;
    @FXML private Label labelInfo;
    @FXML private Label labelTempsBlanc;
    @FXML private Label labelTempsNoir;

    private static final int TAILLE_PLATEAU = 8;
    private static final int TAILLE_CASE = 120;

    private Board plateau;
    
    // Coordonnées de la pièce actuellement sélectionnée par le joueur
    private Integer ligSel = null;
    private Integer colSel = null;

    private int tempsBlanc = 600; // Temps initial en secondes (10 minutes)
    private int tempsNoir = 600;
    private Timeline chrono;

    /**
     * Méthode appelée automatiquement par JavaFX après le chargement du fichier FXML.
     */
    @FXML
    public void initialize() {
        plateau = new Board();
        majAffichage();
        lancerChrono();
    }

    /**
     * Démarre une boucle temporelle qui décrémente l'horloge du joueur actif chaque seconde.
     */
    private void lancerChrono() {
        chrono = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (plateau.getJoueurActif() == PieceColor.BLANC) {
                tempsBlanc--;
                majTemps(labelTempsBlanc, tempsBlanc);
            } else {
                tempsNoir--;
                majTemps(labelTempsNoir, tempsNoir);
            }
        }));
        chrono.setCycleCount(Timeline.INDEFINITE);
        chrono.play();
    }

    /**
     * Formate et affiche le temps restant dans le label correspondant.
     */
    private void majTemps(Label label, int totalSec) {
        int min = totalSec / 60;
        int sec = totalSec % 60;
        label.setText(String.format("%02d:%02d", min, sec));
    }

    /**
     * Reconstruit visuellement la grille JavaFX en fonction de l'état actuel de la matrice Board.
     */
    private void majAffichage() {
        grillePlateau.getChildren().clear();

        for (int lig = 0; lig < TAILLE_PLATEAU; lig++) {
            for (int col = 0; col < TAILLE_PLATEAU; col++) {
                StackPane casePlateau = new StackPane();
                casePlateau.setPrefSize(TAILLE_CASE, TAILLE_CASE);

                // Détermination de la couleur de fond (sélection, case claire, case foncée)
                if (ligSel != null && ligSel == lig && colSel != null && colSel == col) {
                    casePlateau.setStyle("-fx-background-color: #7b61ff;"); 
                } else if ((lig + col) % 2 == 0) {
                    casePlateau.setStyle("-fx-background-color: #f0d9b5;"); 
                } else {
                    casePlateau.setStyle("-fx-background-color: #b58863;"); 
                }

                // Insertion du caractère Unicode de la pièce si la case n'est pas vide
                Piece p = plateau.getPiece(lig, col);
                if (p != null) {
                    Label labelPiece = new Label(getIconePiece(p));
                    labelPiece.setFont(Font.font(80));
                    casePlateau.getChildren().add(labelPiece);
                }

                final int l = lig;
                final int c = col;
                casePlateau.setOnMouseClicked(e -> clicCase(l, c));

                grillePlateau.add(casePlateau, col, lig);
            }
        }
    }

    /**
     * Gère la logique de sélection et de déplacement lors d'un clic sur l'échiquier.
     */
    private void clicCase(int lig, int col) {
        if (ligSel == null && colSel == null) {
            // Premier clic : sélection de la pièce
            Piece pClic = plateau.getPiece(lig, col);
            if (pClic != null && pClic.getCouleur() == plateau.getJoueurActif()) {
                ligSel = lig;
                colSel = col;
            }
        } else {
            // Second clic : annulation ou tentative de déplacement
            if (ligSel == lig && colSel == col) {
                ligSel = null; // Annulation si clic sur la même case
                colSel = null;
            } else {
                boolean deplacementOk = plateau.deplacerPiece(ligSel, colSel, lig, col);
                if (deplacementOk) {
                    ligSel = null;
                    colSel = null;
                } else {
                    // Si le déplacement échoue (règle invalide), on tente de sélectionner la nouvelle case
                    Piece pClic = plateau.getPiece(lig, col);
                    if (pClic != null && pClic.getCouleur() == plateau.getJoueurActif()) {
                        ligSel = lig;
                        colSel = col;
                    } else {
                        ligSel = null;
                        colSel = null;
                    }
                }
            }
        }
        majAffichage(); // Rafraîchissement visuel après chaque interaction
    }

    /**
     * Associe un caractère Unicode à chaque type et couleur de pièce.
     */
    private String getIconePiece(Piece p) {
        return switch (p.getCouleur()) {
            case BLANC -> switch (p.getType()) {
                case ROI -> "♔"; case REINE -> "♕"; case TOUR -> "♖";
                case FOU -> "♗"; case CAVALIER -> "♘"; case PION -> "♙";
            };
            case NOIR -> switch (p.getType()) {
                case ROI -> "♚"; case REINE -> "♛"; case TOUR -> "♜";
                case FOU -> "♝"; case CAVALIER -> "♞"; case PION -> "♟";
            };
        };
    }

    /**
     * Interrompt le jeu, ouvre la boîte de dialogue de signalement, génère le JSON et l'écrit dans un fichier.
     */
    @FXML
    public void actionSignaler() {
        chrono.pause(); // Suspension du temps pendant la rédaction du rapport

        Dialog<String> boite = new Dialog<>();
        boite.setTitle("Signaler une anomalie");
        boite.setHeaderText("Veuillez décrire le comportement anormal :");

        TextArea txtDesc = new TextArea();
        txtDesc.setPrefRowCount(4);
        txtDesc.setWrapText(true);

        VBox conteneur = new VBox(txtDesc);
        boite.getDialogPane().setContent(conteneur);
        boite.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        boite.setResultConverter(btn -> btn == ButtonType.OK ? txtDesc.getText() : null);

        Optional<String> res = boite.showAndWait();

        res.ifPresent(desc -> {
            String json = plateau.genererJSON(desc);
            
            // Écriture du JSON
            try {
                Path cheminFichier = Paths.get("report.json");
                Files.writeString(cheminFichier, json);
                System.out.println("Le fichier a bien ete cree");
            } catch (Exception ex) {
                System.out.println("=> ERREUR : Impossible de creer le fichier : " + ex.getMessage());
            }

            labelInfo.setText("Signalement envoyé !");
        });

        chrono.play(); // Reprise du jeu
    }
}