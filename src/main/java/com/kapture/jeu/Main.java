package com.kapture.jeu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Point d'entrée principal de l'application JavaFX Kapture Client.
 */
public class Main extends Application {
    
    /**
     * Initialise et affiche la fenêtre principale du jeu.
     */
    @Override
    public void start(Stage fenetre) throws Exception {
        FXMLLoader chargeur = new FXMLLoader(Main.class.getResource("game.fxml"));
        Scene scene = new Scene(chargeur.load());
        
        fenetre.setScene(scene);
        
        // Forçage de la mise en plein écran 
        Rectangle2D ecran = Screen.getPrimary().getVisualBounds();
        fenetre.setX(ecran.getMinX());
        fenetre.setY(ecran.getMinY());
        fenetre.setWidth(ecran.getWidth());
        fenetre.setHeight(ecran.getHeight());
        
        fenetre.show();
    }

    public static void main(String[] args) {
        launch();
    }
}