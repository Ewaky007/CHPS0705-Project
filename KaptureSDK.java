package com.kapture.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapture.sdk.buffer.BufferEvenement;
import com.kapture.sdk.model.Evenement;
import com.kapture.sdk.model.Signalement;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class KaptureSDK {

    private final String apiUrl;
    private final HttpClient httpClient;
    private final ObjectMapper mapper;
    private final BufferEvenement buffer;

    private String sessionId;
    private String joueurId;
    private String jeuId;

    public KaptureSDK(String apiUrl) {
        this.apiUrl = apiUrl;
        this.httpClient = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
        this.buffer = new BufferEvenement();
    }

    // Démarre une session
    public void startSession(String joueurId, String jeuId) {
        this.joueurId = joueurId;
        this.jeuId = jeuId;
        this.sessionId = joueurId + "_" + jeuId + "_" + System.currentTimeMillis();
        System.out.println("[Kapture] Session démarrée : " + sessionId);
    }

    // Enregistre un événement dans le buffer
    public void trackEvent(String type, Object data) {
        Evenement evenement = new Evenement(type, data);
        buffer.ajouter(evenement);
        System.out.println("[Kapture] Événement enregistré : " + type);

        // Envoi automatique par lot tous les 10 événements
        if (buffer.getEvenements().size() >= 10) {
            envoyerEvenements();
        }
    }

    // Signale un bug avec l'historique des événements récents
    public void reportBug(String categorie, String description) {
        try {
            Signalement signalement = new Signalement(
                    joueurId, sessionId, categorie, description);

            List<Evenement> historique = buffer.getEvenements();

            Map<String, Object> payload = Map.of(
                    "signalement", signalement,
                    "evenementsAvant", historique);

            String json = mapper.writeValueAsString(payload);
            envoyerRequete("/api/v1/signalements", json);
            System.out.println("[Kapture] Bug signalé : " + categorie);

        } catch (Exception e) {
            System.err.println("[Kapture] Erreur signalement : " + e.getMessage());
        }
    }

    // Termine la session et envoie les événements restants
    public void endSession(boolean victoire) {
        trackEvent(victoire ? "PARTIE_GAGNEE" : "PARTIE_PERDUE", null);
        envoyerEvenements();
        System.out.println("[Kapture] Session terminée : " + sessionId);
        sessionId = null;
        joueurId = null;
        jeuId = null;
    }

    // Envoie les événements du buffer par lot
    private void envoyerEvenements() {
        try {
            List<Evenement> evenements = buffer.getEvenements();
            if (evenements.isEmpty())
                return;

            Map<String, Object> payload = Map.of(
                    "sessionId", sessionId,
                    "events", evenements);

            String json = mapper.writeValueAsString(payload);
            envoyerRequete("/api/v1/evenements/lot", json);
            buffer.vider();

        } catch (Exception e) {
            System.err.println("[Kapture] Erreur envoi événements : " + e.getMessage());
        }
    }

    // Envoie une requête HTTP POST à l'API
    private void envoyerRequete(String endpoint, String json) {
        try {
            HttpRequest requete = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl + endpoint))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> reponse = httpClient.send(
                    requete,
                    HttpResponse.BodyHandlers.ofString());

            System.out.println("[Kapture] Réponse API : " + reponse.statusCode());

        } catch (Exception e) {
            System.err.println("[Kapture] Erreur réseau : " + e.getMessage());
        }
    }
}