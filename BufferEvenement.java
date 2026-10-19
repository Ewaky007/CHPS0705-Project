package com.kapture.sdk.buffer;

import com.kapture.sdk.model.Evenement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BufferEvenement {

    private static final int TAILLE_MAX = 100;
    private final LinkedList<Evenement> buffer = new LinkedList<>();

    public void ajouter(Evenement evenement) {
        if (buffer.size() >= TAILLE_MAX) {
            buffer.removeFirst();
        }
        buffer.addLast(evenement);
    }

    public List<Evenement> getEvenements() {
        return new ArrayList<>(buffer);
    }

    public void vider() {
        buffer.clear();
    }
}