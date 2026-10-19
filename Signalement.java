package com.kapture.sdk.model;

public class Signalement {
    private String joueurId;
    private String sessionId;
    private String description;
    private String categorie;
    private long signaleLe;

    public Signalement(String joueurId, String sessionId,
            String categorie, String description) {
        this.joueurId = joueurId;
        this.sessionId = sessionId;
        this.categorie = categorie;
        this.description = description;
        this.signaleLe = System.currentTimeMillis();
    }

    public String getJoueurId() {
        return joueurId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getDescription() {
        return description;
    }

    public String getCategorie() {
        return categorie;
    }

    public long getSignaleLe() {
        return signaleLe;
    }
}